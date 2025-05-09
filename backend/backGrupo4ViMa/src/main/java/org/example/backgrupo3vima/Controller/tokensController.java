package org.example.backgrupo3vima.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tokens", description = "Gestión de tokens de autenticación para doble factor de seguridad")
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

    @Operation(summary = "Validar credenciales", description = "Permite a un usuario iniciar sesión validando sus credenciales.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso."),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas.")
    })
    @PostMapping("/validarCredenciales")
    public ResponseEntity<Tokens> login(@RequestBody LoginRequest loginRequest) {
        // Verificar si el correo electrónico existe
        Optional<User> user = userRepository.findByCorreo(loginRequest.getCorreo());
        if (!user.isPresent() || !user.get().getContrasenia().equals(loginRequest.getContrasenia())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Generar un nuevo token para el usuario
        Tokens newToken = tokensService.crearTokenParaUsuario((long) user.get().getId());
        return ResponseEntity.ok(newToken);
    }

    @Operation(summary = "Validar token", description = "Valida si un token es válido o ha expirado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token válido."),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado.")
    })
    @PostMapping("/validarToken")
    public ResponseEntity<?> validarToken(@RequestBody ValidarTokenRequest validarTokenRequest) {
        Optional<Tokens> token = tokensRepository.findByToken(validarTokenRequest.getTokenIngresado());
        if (!token.isPresent() || tokensService.isExpired(token.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token inválido o expirado."));
        }

        // Recuperar el usuario asociado al token
        Optional<User> user = userRepository.findById(token.get().getUsuario());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no encontrado."));
        }

        // Verificar si el usuario tiene la sesión activa
        if (!Boolean.TRUE.equals(user.get().getSesionActiva())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Sesión cerrada. Por favor, inicie sesión."));
        }

        // Devolver el correo del usuario junto con el mensaje de éxito
        return ResponseEntity.ok(Map.of("mensaje", "Token válido.", "correo", user.get().getCorreo()));
    }
}
