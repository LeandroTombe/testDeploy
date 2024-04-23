package com.oficial.C1739.Extern.Email.Controller;


import com.oficial.C1739.Extern.Email.Entity.EstructuraMail;
import com.oficial.C1739.Extern.Email.Entity.MailToken;
import com.oficial.C1739.Extern.Email.Repository.MailTokenRepository;
import com.oficial.C1739.Extern.Email.Service.MailService;
import com.oficial.C1739.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/email")
public class MailController {

    @Autowired
    MailService mailService;

    @Autowired
    MailTokenRepository mailTokenRepository;

    @Autowired
    UserService userService;



    @PostMapping("/send/{mail}")
    public ResponseEntity<String> enviarEmail(@PathVariable String mail, @RequestBody EstructuraMail estructuraMail){

        mailService.enviarEmail(mail,estructuraMail);

        return ResponseEntity.ok().body("El mensaje ha sido enviado exitosamente");
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verificarEmail(@RequestParam("token") String token){

        MailToken mailToken= mailTokenRepository.findByToken(token);

        if (mailToken.getUsuario().isEnabled()){
            return  new ResponseEntity<>("La cuenta ya ha sido confirmada, por favor logueese",HttpStatus.BAD_REQUEST);
        }

        String resultadoVerificado= userService.validarToken(token);

        if (resultadoVerificado.equalsIgnoreCase("valido")){
            return new ResponseEntity<>("El email ha sido verificado exitosamente. ahora puedes loguearte con tu cuenta", HttpStatus.OK);
        }
        return new ResponseEntity<>("Token invalido",HttpStatus.BAD_REQUEST);

    }
}
