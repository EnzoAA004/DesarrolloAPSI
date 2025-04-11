package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.tokensEntity;
import org.springframework.stereotype.Service;

@Service
public interface tokensService {
    tokensEntity actualizarTokens();
    boolean isExpired(tokensEntity token);

    tokensEntity crearToken();
}
