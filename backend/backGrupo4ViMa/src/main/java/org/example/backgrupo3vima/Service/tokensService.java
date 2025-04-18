package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Tokens;
import org.springframework.stereotype.Service;

@Service
public interface tokensService {
    Tokens actualizarTokens();
    boolean isExpired(Tokens token);

    Tokens crearTokenParaUsuario(Long userId);


    void enviarCorreo(String correo, String asunto, String mensaje);

}
