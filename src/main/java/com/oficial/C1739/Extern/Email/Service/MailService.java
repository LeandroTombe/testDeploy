package com.oficial.C1739.Extern.Email.Service;


import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Extern.Email.Entity.EstructuraMail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarEmail(String mail, EstructuraMail estructuraMail){
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setSubject(estructuraMail.getSujeto());
        simpleMailMessage.setText(estructuraMail.getMensaje());
        simpleMailMessage.setTo(mail);

        javaMailSender.send(simpleMailMessage);
    }

    public void enviarVerificacionEmail(String url, Usuario nuevoUsario) throws MessagingException, UnsupportedEncodingException {
        String subject = "Verificar email";
        //String senderName = "el nombre de nuestro proyecto";
        String mailContent = "<p> hola, "+ nuevoUsario.getNombre()+ ", </p>"+
                "<p>Gracias por registrarse ,"+
                "Por favor, haga click en el click de abajo para confirmar su registro</p>"+
                "<a href=\"" +url+ "\">verificar su email para activar la cuenta</a>"+
                "<p> muchas gracias!!";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(nuevoUsario.getCorreo());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(String url, Usuario usuario) throws MessagingException, UnsupportedEncodingException {
        String subject = "Verificacion para el reseteo de password";
        String senderName = "No country";
        String mailContent = "<p> hola , "+ usuario.getNombre()+ ", </p>"+
                "<p><b>Has pedido resetear el password. </b>"+"" +
                "Por favor, haga click en el link de abajo para completar la accion</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> No country";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(fromEmail);
        messageHelper.setTo(usuario.getCorreo());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }

}
