package com.oficial.C1739.Event.Listener;

import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Event.RegistroCompletadoE;
import com.oficial.C1739.Extern.Email.Service.MailService;
import com.oficial.C1739.Service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
//Con esta lo que hacemos es escuchar cuando se dispara el evento, En este caso cuando
//creemos un usuario, se disparara el evento de RegistroCompletadoE y esta clase se encargada de esucharlo

public class RegistroCompletadoEL implements ApplicationListener<RegistroCompletadoE> {

    @Autowired
    private  UserService userService;

    @Autowired
    MailService mailService;


    private Usuario nuevoUsuario;




    @Override
    public void onApplicationEvent(RegistroCompletadoE event) {
        //1) Se obtiene el el usuario registrado
        nuevoUsuario= event.getUsuario();

        //2) crear el token de verificacion para le usuario
        String tokenVerificacion= UUID.randomUUID().toString();

        //3)Guardar el token de verificacion
        userService.verificarToken(nuevoUsuario,tokenVerificacion);

        //4) contruimos la url para verificar el token
        String url = event.getUrlAplicacion()+"/api/email/verifyEmail?token="+tokenVerificacion;

        //5) enviamos el mail
        try {
            mailService.enviarVerificacionEmail(url,nuevoUsuario);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



}
