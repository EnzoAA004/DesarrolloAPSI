package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.dto.LoginRequest;
import org.example.backgrupo3vima.Entity.Tokens;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Entity.dto.ValidarTokenRequest;
import org.example.backgrupo3vima.Repository.tokensRepository;
import org.example.backgrupo3vima.Repository.userRepository;
import org.example.backgrupo3vima.Service.tokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

    @PostMapping("/validarCredenciales")
    public ResponseEntity<Tokens> login(@RequestBody LoginRequest loginRequest) {
        // Verificar si el correo electrónico existe
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent() || !user.get().getContrasenia().equals(loginRequest.getContrasenia())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generar un nuevo token para el usuario
        Tokens newToken = tokensService.crearTokenParaUsuario((long) user.get().getId());
        return ResponseEntity.ok(newToken);
    }

    @PostMapping("/validarToken")
    public ResponseEntity<?> validarToken(@RequestBody ValidarTokenRequest validarTokenRequest) {
        Optional<Tokens> token = tokensRepository.findByToken(validarTokenRequest.getTokenIngresado());
        if (!token.isPresent() || tokensService.isExpired(token.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token inválido o expirado."));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Token válido."));
    }
}
