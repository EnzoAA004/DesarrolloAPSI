package org.example.backgrupo3vima.Service;


import org.example.backgrupo3vima.Entity.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Tokens crearToken() {
        String token = generateRandomToken(6);
        Tokens newToken = new Tokens();
        newToken.setToken(token);
        newToken.setFechaCreacion(LocalDate.now());
        return tokensRepository.save(newToken);
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

    //Token para iniciar sesion - mandar mail


    //Token para restablecer contraseña - mandar mail



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
