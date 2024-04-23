package com.oficial.C1739.Service;


import com.oficial.C1739.Entity.JwtToken;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Event.RegistroCompletadoE;
import com.oficial.C1739.Exception.InvalidPasswordException;
import com.oficial.C1739.Extern.Email.Service.MailService;
import com.oficial.C1739.Repository.JwtTokenRepository;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.oficial.C1739.dto.GuardarUsuario;
import com.oficial.C1739.dto.ProfileDTO;
import com.oficial.C1739.dto.UsuarioRegistrado;
import com.oficial.C1739.dto.auth.AuthenticationRequest;
import com.oficial.C1739.dto.auth.AuthenticationResponse;
import com.oficial.C1739.dto.auth.PasswordRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticateService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    ProfileService profileService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MailService mailService;



    public  Boolean cambiarPassword(PasswordRequest passwordRequest) {

        if (!passwordRequest.getNuevoPassword().equals(passwordRequest.getConfirmarNuevoPassword())){
            throw new InvalidPasswordException("El password no coincide");
        }

        if (passwordRequest.getConfirmarNuevoPassword().isEmpty() || passwordRequest.getConfirmarNuevoPassword().isEmpty()){
            throw  new InvalidPasswordException("Uno de los campos de password esta vacio");
        }

        try {
            //Obtengo el usuario logueado
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            //Buscamos el correo a traves del SecurityContextHolder con su metodo principal

            String correo= (String) auth.getPrincipal();
            if ((correo==null) || (correo.equals("anonymousUser")) || (correo.isEmpty())){
                throw new RuntimeException(" No esta logueado");
            }
            String nuevoPassword= passwordRequest.getNuevoPassword();
            Boolean validarPassword= userService.cambiarPassword(nuevoPassword,correo);
            return validarPassword;
        } catch (Exception e){
            throw new RuntimeException("error en el cambio"+ e.getMessage());
        }

    }

    public Boolean resetPassword(String userEmail,HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {

        Optional<Usuario> findUser= userService.findOneByCorreo(userEmail);
        if (findUser.isEmpty()){
            throw new UsernameNotFoundException("No se encuentra el usuario");
        }
        //crear la url para resetear el password
        String token= UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(findUser.get(),token);
        String passwordResetUrl= passwordResetEmailLink(findUser.get(), urlAplicacion(servletRequest),token);
        return true;
    }

    private String passwordResetEmailLink(Usuario usuario, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/register/resetPassword?token="+passwordToken;
        mailService.sendPasswordResetVerificationEmail(url,usuario);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }


    public Usuario registrarUnUsuario(GuardarUsuario nuevoUsuario) {
        Usuario usuario= userService.registrarUnUsuario(nuevoUsuario);

        //En esta parte, se settea el token con su respectiva logica
        String jwt= jwtService.generarTokenJWT(usuario, generarExtraClaims(usuario));

        //cuando se crea el usuario registrado, guardamos su token para la creacion del logout
        GuardarUsuarioToken(jwt,usuario);

        //creamos el perfil para guardar sus datos con campos extras
        //SaveProfileDTO saveProfileDTO= new SaveProfileDTO();


        UsuarioRegistrado usuarioDTO= new UsuarioRegistrado();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setRol(usuario.getRol().getNombre());
        return usuario;
    }

    private Map<String, Object> generarExtraClaims(Usuario usuario) {
        Map<String, Object> claims= new HashMap<>();
        claims.put("correo", usuario.getCorreo());
        claims.put("rol", usuario.getRol().getNombre());
        return claims;

    }



    public AuthenticationResponse login(AuthenticationRequest authenticationrequest) {

        validarLogin(authenticationrequest);
        // creamos un objeto authentication con el correo y la contraseña
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationrequest.getCorreo(), authenticationrequest.getPassword());

        // En esta parte se autentica el usuario,
        //el authenticate() buscara un proveedor para que resuelva el login del objeto UsernamePasswordAuthenticationToken
        // Para nuestro caso es el DaoAuthenticationProvider que configuramos en el HttpSecurityConfig con authenticationProvider(daoAuthenticationProvider)
        //Realiza la validacion que si existe a traves del passwordencorder ya que el password viene plano y este va a parsear con su algoritmo Bycript para comparar la base de datos
        authenticationManager.authenticate(authentication);

        //Obtengo los detalles del usuario que se acaba de loguear
        UserDetails usuario= userService.findOneByCorreo(authenticationrequest.getCorreo()).get();
        String jwt= jwtService.generarTokenJWT(usuario, generarExtraClaims((Usuario) usuario));
        GuardarUsuarioToken(jwt, usuario);
        //Genero la respuesta con el token
        AuthenticationResponse authenticationResponse= new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);

        return authenticationResponse;

    }

    private void validarLogin(AuthenticationRequest authenticationrequest) {
        Optional<Usuario> usuario= userService.findOneByCorreo(authenticationrequest.getCorreo());
        if (usuario.isEmpty()) {
            throw new RuntimeException("el correo del usuario es invalido");
        }
        if (!passwordEncoder.matches(authenticationrequest.getPassword(), usuario.get().getPassword())) {
                throw new RuntimeException("password incorrecto");
        };

    }

    private void GuardarUsuarioToken(String jwt, UserDetails usuario) {
        JwtToken token = new JwtToken();
        token.setJwt(jwt);
        token.setUsuario((Usuario) usuario);
        token.setExpireAt(jwtService.extraerExpiracion(jwt));

        jwtTokenRepository.save(token);

    }

    public Boolean validarToken(String token) {
        try{
            jwtService.extraerUsuarioDelToken(token);
            return true;
        }catch (Exception e){
            System.out.println("Error al validar el token" + e.getMessage());
            return false;
        }

    }


    /**
     * ¿Qué es SecurityContextHolder?
     *
     * Almacenar el contexto de seguridad actual: Este contexto contiene información sobre el usuario autenticado,
     * como su nombre, roles y permisos.
     *
     * Proporcionar acceso al contexto de seguridad desde cualquier lugar de la aplicación:
     * Esto permite verificar si un usuario tiene acceso a un recurso o realizar acciones en su nombre.
     */
    public ProfileDTO getProfile() {
        //Obtengo el usuario logueado
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();

        //verificar si auth es una instancia de UsernamePasswordAuthenticationToken, si es asi, obtengo lo parseo y obtengo el usuario
        //Esto nos sirve como buena practica para cuando tengamos mas de una implementacion de autenticacion
        if(auth instanceof  UsernamePasswordAuthenticationToken  authToken){

            //Buscamos el correo a traves del SeurityContextHolder con su metodo principal
           String correo= (String) authToken.getPrincipal();

           Usuario usuario=  userService.findOneByCorreo(correo).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
           //devolvemos los atributos que consideramos relevante del perfil a traves de un dto
            ProfileDTO profileDTO= new ProfileDTO();
            profileDTO.setNombre(usuario.getNombre());
            profileDTO.setApellido(usuario.getApellido());
            profileDTO.setCorreo(usuario.getCorreo());
            profileDTO.setRol(usuario.getRol().getNombre());
            profileDTO.setAge(usuario.getPerfil().getAge());
            profileDTO.setPhone(usuario.getPerfil().getPhone());
            profileDTO.setGit(usuario.getPerfil().getGit());
            profileDTO.setAvatar(usuario.getPerfil().getAvatar());
            profileDTO.setLinkedin(usuario.getPerfil().getLinkedin());
            profileDTO.setEspecialidad(usuario.getPerfil().getEspecialidad());
            profileDTO.setNacionalidad(usuario.getPerfil().getNacionalidad());




           return profileDTO;
        }
        return null;

    }

    public void logout(HttpServletRequest request) {

        String jwt= jwtService.extractJwtFromRequest(request);

        if (jwt==null || !StringUtils.hasText(jwt)){
            return;
        }

        Optional<JwtToken> jwtToken= jwtTokenRepository.findByjwt(jwt);

        if (jwtToken.isPresent() && jwtToken.get().getValid()){
            jwtToken.get().setValid(false);
            jwtTokenRepository.save(jwtToken.get());
        }
    }

    private String urlAplicacion(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
