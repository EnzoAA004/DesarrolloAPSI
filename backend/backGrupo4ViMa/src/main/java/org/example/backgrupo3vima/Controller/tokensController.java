package org.example.backgrupo3vima.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.example.backgrupo3vima.Entity.dto.LoginRequest;
import org.example.backgrupo3vima.Entity.tokensEntity;
import org.example.backgrupo3vima.Entity.userEntity;
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
@Tag(name = "Tokens", description = "Operaciones relacionadas con tokens de autenticación")
public class tokensController {

    @Autowired
    private tokensService tokensService;

    @Autowired
    private tokensRepository tokensRepository;

    @Autowired
    private userRepository userRepository;

    @DeleteMapping
    @Operation(
            summary = "Actualizar tokens",
            description = "Este endpoint actualiza el token activo en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token actualizado correctamente"),
            }
    )
    public ResponseEntity<Void> actualizarTokens () {
        tokensEntity token = tokensService.actualizarTokens();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login con token",
            description = "Permite loguearse si el email existe y el token es válido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login exitoso",
                            content = @Content(schema = @Schema(implementation = tokensEntity.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
            }
    )
    public ResponseEntity<tokensEntity> login(@RequestBody LoginRequest loginRequest) {
        Optional<userEntity> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<tokensEntity> token = tokensRepository.findByUser((userRepository) user.get());
        if (!token.isPresent() || !tokensService.isExpired(token.get()) || !token.get().getToken().equals(loginRequest.getToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        tokensEntity newToken = tokensService.crearToken();
        return ResponseEntity.ok(newToken);
    }
}
