package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.userEntity;
import org.example.backgrupo3vima.Service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/Usuario")
public class userController {

    @Autowired
    private userService userService;

    @Operation(summary = "Obtener todos los usuarios registrados")
    @GetMapping
    public ResponseEntity<List<userEntity>> todosLosUsuarios() {
        List<userEntity> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @Operation(summary = "Actualizar campos específicos de un usuario por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<userEntity> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        userEntity usuario = userService.actualizarUsuario(id, updates);
        return ResponseEntity.ok(usuario);
    }
}
