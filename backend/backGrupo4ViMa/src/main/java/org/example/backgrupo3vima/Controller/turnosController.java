package org.example.backgrupo3vima.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.backgrupo3vima.Entity.*;
import org.example.backgrupo3vima.Entity.dto.TurnoDTO;
import org.example.backgrupo3vima.Repository.tokensRepository;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.example.backgrupo3vima.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/turnos")
@Tag(name = "Turnos", description = "Gestión de turnos médicos")

public class turnosController {
    @Autowired
    private turnosService turnosService;

    @Autowired
    private turnosRepository turnosRepository;

    @Autowired
    private doctorService doctorService;

    @Autowired
    private notificacionService notificacionService;

    @Autowired
    private tokensService tokensService;
    @Autowired
    private tokensRepository tokensRepository;

    @Autowired
    private userService userService;

    @Operation(summary = "Crear un turno", description = "Permite crear un nuevo turno para un usuario con un doctor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno confirmado exitosamente."),
            @ApiResponse(responseCode = "409", description = "El turno no está disponible.")
    })
    @PostMapping("/crearTurno")
    public ResponseEntity<String> crearTurno(@RequestBody TurnoDTO turnoDTO) {

        // Verificar si el usuario tiene la sesión activa
        User usuario = turnosService.obtenerUsuarioPorId(turnoDTO.getUsuarioId());
        if (!userService.validarSesionActiva(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La sesión del usuario no está activa. Por favor, inicie sesión.");
        }

        boolean disponible = doctorService.verificarDisponibilidadTurno(
                turnoDTO.getDoctorId(),
                turnoDTO.getFechaTurno().toString(),
                turnoDTO.getFechaTurno().toString(),
                turnoDTO.getHoraInicio().toString(),
                turnoDTO.getHoraFin().toString()
        );

        if (!disponible) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El turno no está disponible.");
        }

        // Crear y guardar el nuevo turno
        Turnos nuevoTurno = new Turnos();
        nuevoTurno.setEstado(TurnoEstado.PENDIENTE);

        Doctor doctor = doctorService.obtenerDoctorPorId(turnoDTO.getDoctorId());
        nuevoTurno.setDoctor(doctor);
        nuevoTurno.setUsuario(usuario);
        nuevoTurno.setFechaTurno(turnoDTO.getFechaTurno());
        nuevoTurno.setHoraInicio(turnoDTO.getHoraInicio());
        nuevoTurno.setHoraFin(turnoDTO.getHoraFin());

        turnosRepository.save(nuevoTurno);

        // ✅ Crear el token
        Tokens token = tokensService.crearTokenParaUsuario((long) usuario.getId());

        // ✅ Asociar el turno al token
        token.setTurno(nuevoTurno);
        tokensRepository.save(token); // guardar token con turno asociado

        // ✅ Enviar el token por correo
        String mensajeCorreo = String.format(
                "Hola %s,\n\nTu turno ha sido generado. Para confirmarlo, por favor ingresa el siguiente código:\n\n%s\n\nGracias.",
                usuario.getNombre(), token.getToken()
        );

        tokensService.enviarCorreo(usuario.getCorreo(), "Confirmación de Turno", mensajeCorreo);

        // ✅ Enviar notificación interna
        notificacionService.crearYEnviarNotificacion(
                turnoDTO.getUsuarioId(),
                "Nuevo Turno",
                "Tu turno con el doctor " + doctor.getNombre() + " ha sido generado. Revisa tu correo para confirmarlo.",
                "turno_logo.png"
        );

        return ResponseEntity.ok("Turno creado exitosamente. Se ha enviado un token a tu correo.");
    }



    @Operation(summary = "Confirmar un turno", description = "Permite confirmar un turno existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno confirmado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado.")
    })
    @PutMapping("/confirmarTurno/{id}")
    public ResponseEntity<String> confirmarTurno(@PathVariable int id) {
        Turnos turno = turnosRepository.findById(id).orElse(null);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado.");
        }

        User usuario = turno.getUsuario();
        if (usuario == null || !userService.validarSesionActiva(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    usuario == null ? "Usuario no encontrado." : "La sesión del usuario no está activa. Por favor, inicie sesión."
            );
        }

        turno.setEstado(TurnoEstado.CONFIRMADO);
        turnosRepository.save(turno);

        // Crear y enviar notificación al usuario
        notificacionService.crearYEnviarNotificacion(
                usuario.getId(),
                "Turno Confirmado",
                "Tu turno ha sido confirmado.",
                "confirmacion_logo.png"
        );

        return ResponseEntity.ok("Turno confirmado exitosamente.");
    }

    @Operation(summary = "Cancelar un turno", description = "Permite cancelar un turno existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno cancelado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado.")
    })
    @PutMapping("/cancelarTurno/{id}")
    public ResponseEntity<String> cancelarTurno(@PathVariable int id) {
        Turnos turno = turnosRepository.findById(id).orElse(null);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado.");
        }

        User usuario = turno.getUsuario();
        if (usuario == null || !userService.validarSesionActiva(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    usuario == null ? "Usuario no encontrado." : "La sesión del usuario no está activa. Por favor, inicie sesión."
            );
        }

        // Actualizar el estado del turno a CANCELADO
        turno.setEstado(TurnoEstado.CANCELADO);

        // Eliminar las relaciones con el usuario y el doctor
        turno.setUsuario(null);
        turno.setDoctor(null);

        // Guardar los cambios en el repositorio
        turnosRepository.save(turno);

        // Crear y enviar notificación al usuario
        notificacionService.crearYEnviarNotificacion(
                usuario.getId(),
                "Turno Cancelado",
                "Tu turno ha sido cancelado.",
                "cancelacion_logo.png"
        );

        return ResponseEntity.ok("Turno cancelado exitosamente.");
    }


    @Operation(summary = "Listar turnos por usuario", description = "Devuelve una lista de turnos pasados y futuros de un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida exitosamente.")
    })
    //Listar turnos por usuario
    @GetMapping("/listarTurnosPorUsuario/{usuarioId}")
    public ResponseEntity<Object> listarTurnosPorUsuario(@PathVariable int usuarioId) {
        User usuario = userService.obtenerPorId((long) usuarioId);
        if (usuario == null || !userService.validarSesionActiva(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    usuario == null ? "Usuario no encontrado." : "La sesión del usuario no está activa. Por favor, inicie sesión."
            );
        }

        List<Turnos> turnos = turnosService.listarTurnosPorUsuario(usuarioId);

        List<Turnos> turnosPasados = turnos.stream()
                .filter(turno -> turno.getFechaTurno().isBefore(LocalDate.now()))
                .toList();

        List<Turnos> turnosFuturos = turnos.stream()
                .filter(turno -> !turno.getFechaTurno().isBefore(LocalDate.now()))
                .toList();

        return ResponseEntity.ok(Map.of(
                "turnosPasados", turnosPasados,
                "turnosFuturos", turnosFuturos
        ));
    }


    @Operation(summary = "Editar un turno", description = "Permite al doctor editar la nota de un turno pasado y/o agregar un archivo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turno editado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado."),
            @ApiResponse(responseCode = "400", description = "No se puede editar un turno futuro.")
    })
    @PatchMapping("/editarTurno/{id}")
    public ResponseEntity<String> editarTurno(
            @PathVariable int id,
            @RequestParam(required = false) String nota,
            @RequestParam(required = false) MultipartFile archivo) {

        Turnos turno = turnosRepository.findById(id).orElse(null);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado.");
        }

        User usuario = turno.getUsuario();
        if (usuario == null || !userService.validarSesionActiva(usuario.getCorreo())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    usuario == null ? "Usuario no encontrado." : "La sesión del usuario no está activa. Por favor, inicie sesión."
            );
        }

        if (turno.getFechaTurno().isAfter(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede editar un turno futuro.");
        }

        if (nota != null) {
            turno.setNota(nota);
        }

        if (archivo != null) {
            // Lógica para guardar el archivo (por ejemplo, en el sistema de archivos o en la base de datos)
            String rutaArchivo = turnosService.guardarArchivo(archivo);
            turno.setArchivoAdjunto(rutaArchivo); // Asegúrate de tener un campo para la ruta del archivo en la entidad Turnos
        }

        turnosRepository.save(turno);

        return ResponseEntity.ok("Turno editado exitosamente.");
    }


    //Listar turnos por estado, que puede ser: CONFIRMADO, PENDIENTE o CANCELADO
    @Operation(summary = "Listar turnos por estado", description = "Devuelve una lista de turnos filtrados por estado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida exitosamente."),
            @ApiResponse(responseCode = "400", description = "Estado inválido.")
    })
    @GetMapping("/listarTurnosPorEstado")
    public ResponseEntity<Object> listarTurnosPorEstado(@RequestParam String estado) {
        try {
            TurnoEstado turnoEstado = TurnoEstado.valueOf(estado.toUpperCase());
            List<Turnos> turnos = turnosRepository.findByEstado(turnoEstado).stream()
                    .filter(turno -> turno.getUsuario() != null && userService.validarSesionActiva(turno.getUsuario().getCorreo()))
                    .toList();
            return ResponseEntity.ok(turnos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estado inválido. Los estados válidos son: CONFIRMADO, PENDIENTE, CANCELADO.");
        }
    }



}
