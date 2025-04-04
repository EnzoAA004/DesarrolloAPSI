package Controller;

import Entity.dto.LoginRequest;
import Entity.tokensEntity;
import Entity.userEntity;
import Repository.tokensRepository;
import Repository.userRepository;
import Service.tokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tokens")
public class tokensController {
    @Autowired
    private tokensService tokensService;

    @Autowired
    private tokensRepository tokensRepository;

    @Autowired
    private userRepository userRepository;

    @DeleteMapping
    public ResponseEntity<Void> actualizarTokens () {
        tokensEntity token = tokensService.actualizarTokens();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<tokensEntity> login(@RequestBody LoginRequest loginRequest) {
        // Verificar si el correo electrónico existe
        Optional<userEntity> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Verificar si el token ingresado es correcto y no ha expirado
        Optional<tokensEntity> token = tokensRepository.findByUserId(Long.valueOf(user.get().getId()));
        if (!token.isPresent() || !tokensService.isExpired(token.get()) || !token.get().getToken().equals(loginRequest.getToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generar un nuevo token utilizando una librería de seguridad
        tokensEntity newToken = tokensService.crearToken();
        return ResponseEntity.ok(newToken);
    }
}
