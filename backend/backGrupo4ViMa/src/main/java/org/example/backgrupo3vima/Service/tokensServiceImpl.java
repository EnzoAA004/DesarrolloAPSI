package org.example.backgrupo3vima.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import org.example.backgrupo3vima.Entity.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.example.backgrupo3vima.Repository.tokensRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//import com.resend.*;

@Service
public class tokensServiceImpl implements tokensService {

    @Autowired
    private tokensRepository tokensRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(fixedRate = 86400000) // 24 hours in milisegundos
    @Override
    public Tokens actualizarTokens() {
        tokensRepository.deleteAll();
        return null;
    }

    //Compara la fecha de expiracion del token con la fecha actual
    @Override
    public boolean isExpired(Tokens token) {
        Date fechaCreacion = Date.from(token.getFechaCreacion().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return fechaCreacion.before(new Date());
    }

    @Override
    public Tokens crearTokenParaUsuario(Long userId) {
        String token = generateRandomToken(4); // Generate a random token
        Tokens newToken = new Tokens();
        newToken.setToken(token);
        newToken.setFechaCreacion(LocalDate.now());
        newToken.setId(Math.toIntExact(userId)); // Associate the token with the user
        tokensRepository.save(newToken); // Save the token in the repository
        return newToken;
    }

    private String generateRandomToken(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            token.append(characters.charAt(index));
        }
        return token.toString();
    }


    @Override
    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);
        javaMailSender.send(email);
    }

    @Override
    public void enviarCorreoSoporte(String destinatario, String asunto, String mensaje, String correoUsuario) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje, false);
            helper.setReplyTo(correoUsuario);  // <- Esto permite responder al usuario

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }


/*
    public class Main {
        public static void main(String[] args) {
            Resend resend = new Resend("re_55H8dToW_L9EvXWC6WcyScS528YgtcrTS");

            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .from("onboarding@resend.dev")
                    .to("frfabrello@uade.edu.ar")
                    .subject("Hello World")
                    .html("<p>Congrats on sending your <strong>first email</strong>!</p>")
                    .build();

            SendEmailResponse data = resend.emails().send(sendEmailRequest);
        }
    }
*/

}
