package com.oficial.C1739.Controller;


import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Event.RegistroCompletadoE;
import com.oficial.C1739.Service.AuthenticateService;
import com.oficial.C1739.dto.LogoutResponse;
import com.oficial.C1739.dto.ProfileDTO;
import com.oficial.C1739.dto.auth.AuthenticationRequest;
import com.oficial.C1739.dto.auth.AuthenticationResponse;
import com.oficial.C1739.dto.GuardarUsuario;
import com.oficial.C1739.dto.UsuarioRegistrado;
import com.oficial.C1739.dto.auth.PasswordForgotRequestUtil;
import com.oficial.C1739.dto.auth.PasswordRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private ApplicationEventPublisher publisher;


    /**
     * Los objetos ResponseEntity se utiliza para representar la respuesta HTTP de un controlador REST.
     * Puede contener cualquier tipo de objeto, como una cadena, un objeto JSON o un archivo.
     * Proporcionan información adicional sobre la respuesta, como el tipo de contenido o la longitud del cuerpo.
     */

    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid GuardarUsuario nuevoUsuario, final HttpServletRequest request) {

        Usuario usuario = authenticateService.registrarUnUsuario(nuevoUsuario);
        publisher.publishEvent(new RegistroCompletadoE(usuario,urlAplicacion(request)));

        return ResponseEntity.status(HttpStatus.CREATED).body("El usuario se ha registrado correctamente, por favor verifica tu email para la confirmacion del registro");

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest Authenticationrequest) {
        AuthenticationResponse Authenticationresponse = authenticateService.login(Authenticationrequest);
        return ResponseEntity.status(HttpStatus.OK).body(Authenticationresponse);
    }


    @GetMapping("/validar")
    public ResponseEntity<Boolean> ValidarToken(@RequestParam String token){
        Boolean isTokenValid= authenticateService.validarToken(token);
        return ResponseEntity.ok(isTokenValid);
    }


    //modificar password en caso de haberla perdido
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPasswordRequest(
            @RequestBody PasswordForgotRequestUtil passwordForgotRequestUtil,
            final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        try {
            Boolean nuevoPassword = authenticateService.resetPassword(passwordForgotRequestUtil.getEmail(), servletRequest);
            return ResponseEntity.ok("El password ha sido reseteado exitosamente");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Ha ocurrido un error:" + e.getMessage());
        }

    }
    //modificar password en el perfil
    @PostMapping("/cambiarpassword")
    public ResponseEntity<String> cambiarPassword(@RequestBody PasswordRequest passwordRequest){
        try {
            Boolean validarCambioPassword= authenticateService.cambiarPassword(passwordRequest);
            if (validarCambioPassword) {
                return ResponseEntity.ok("Se te ha enviado un correo para cambiar el password");
            } else {
                throw new RuntimeException("no se pudo");
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error al cambiar el password:" +e.getMessage());
        }


    }


    //mandar la url modificada para el registro
    private String urlAplicacion(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }


    /*
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout (HttpServletRequest request){
        authenticateService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Logout exitoso"));
    }
    */
}
