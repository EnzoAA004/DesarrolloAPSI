package Service;

import Entity.tokensEntity;
import org.springframework.stereotype.Service;

@Service
public interface tokensService {
    tokensEntity actualizarTokens();
    boolean isExpired(tokensEntity token);

    tokensEntity crearToken();
}
