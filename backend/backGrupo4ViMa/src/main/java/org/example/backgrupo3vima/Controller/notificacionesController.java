package org.example.backgrupo3vima.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.aspectj.weaver.ast.Not;
import org.example.backgrupo3vima.Entity.Notificaciones;
import org.example.backgrupo3vima.Service.notificacionService;
import org.example.backgrupo3vima.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@Tag(name = "Notificaciones", description = "Gestión de notificaciones del sistema según el usuario")

public class notificacionesController {

    @Autowired
    private notificacionService notificacionService;

    @Autowired
    private userService userService;

    @Operation(summary = "Obtener todos las notificaciones", description = "Devuelve una lista de todas las notificaciones en el sistema para ese usuario.")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<List<Notificaciones>> todasLasNotificaciones(@PathVariable Long id) {
        List<Notificaciones> notificacionesList = notificacionService.getAllNotificacionesByUserId(id);
        return ResponseEntity.ok(notificacionesList);
    }



}
