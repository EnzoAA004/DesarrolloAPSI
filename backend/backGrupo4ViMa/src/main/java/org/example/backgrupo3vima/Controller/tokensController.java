package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.dto.LoginRequest;
import org.example.backgrupo3vima.Entity.Tokens;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Repository.tokensRepository;
import org.example.backgrupo3vima.Repository.userRepository;
import org.example.backgrupo3vima.Service.tokensService;
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
        Tokens token = tokensService.actualizarTokens();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(@RequestBody LoginRequest loginRequest) {
        // Verificar si el correo electrónico existe
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Verificar si el token ingresado es correcto y no ha expirado
        Optional<Tokens> token = tokensRepository.findByUserId(Long.valueOf(user.get().getId()));
        if (!token.isPresent() || !tokensService.isExpired(token.get()) || !token.get().getToken().equals(loginRequest.getToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generar un nuevo token utilizando una librería de seguridad
        Tokens newToken = tokensService.crearToken();
        return ResponseEntity.ok(newToken);
    }
}
