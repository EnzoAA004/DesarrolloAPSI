package org.example.backgrupo3vima.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Entity.dto.DoctorDTO;
import org.example.backgrupo3vima.Service.doctorService;
import org.example.backgrupo3vima.Service.doctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.backgrupo3vima.Service.turnosService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@Tag (name = "Doctores", description = "Gestión de doctores del sistema")

public class doctorController {
    @Autowired
    private doctorService doctorService;

    @Autowired
    private turnosService turnosService;


    @Operation(summary = "Listar todos los doctores", description = "Devuelve una lista con todos los doctores disponibles en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de doctores obtenida exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron doctores."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta.")
    })
    //Busqueda de todos los doctores para pruebas
    @GetMapping("/doctores")
    public ResponseEntity<List<Doctor>> listarDoctores(){
        List<Doctor> doctores = doctorService.todosLosDoctores();
        return ResponseEntity.ok(doctores);
    }


    /*
    //Busqueda de doctor por especialidadMedica y su nombre
    @GetMapping("/doctores/especialidad")
    public ResponseEntity<List<Doctor>> listarDoctoresPorEspecialidad(int idEspecialidad){
        List<Doctor> doctores = doctorService.buscarPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(doctores);
    }

    //Busqueda de doctor por profesional-nombre
    @GetMapping("/doctores/profesional")
    public ResponseEntity<List<Doctor>> listarDoctoresPorNombreCompleto(String nombreCompleto) {
        List<Doctor> doctores = doctorService.buscarPorNombreCompleto(nombreCompleto);
        return ResponseEntity.ok(doctores);
    }*/

    //Crear doctor
    @Operation(summary = "Crear un nuevo doctor", description = "Permite crear un nuevo doctor en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doctor creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
    })
    @PostMapping("/crear")
    public ResponseEntity<String> crearDoctor(@RequestBody DoctorDTO doctorDTO) {
        try {
            doctorDTO.setEliminado(false); // Establecer eliminado como false por defecto
            doctorService.crearDoctor(doctorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Doctor creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el doctor: " + e.getMessage());
        }
    }

    //"Eliminar doctor" de forma logica
    @Operation(summary = "Eliminar doctor lógicamente", description = "Marca a un doctor como inactivo en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor eliminado lógicamente."),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
    })
    @PatchMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarDoctor(@PathVariable int id) {
        Doctor doctor = doctorService.obtenerDoctorPorId(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado.");
        }

        doctor.setActivo(false); // Marcar como inactivo
        doctorService.actualizarDoctor(doctor); // Guardar cambios
        return ResponseEntity.ok("Doctor eliminado lógicamente.");
    }


    //Mostrar personalmente solo 1 doctor al seleccionarlo
    @Operation(summary = "Obtener doctor por ID", description = "Devuelve la información de un doctor según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctor encontrado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> obtenerDoctorPorId(@PathVariable int id) {
        Doctor doctor = doctorService.obtenerDoctorPorId(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(doctor);
    }



    /*Falta reorganizar la seccion de filtrado, para que solo en un metodo, me llame a los consiguientes si se encuentra
      algun cambio en las estrellas, nombre y fecha de filtrado.*/
    /*
    @Operation(summary = "Filtrar doctores por calificación", description = "Devuelve una lista de doctores con una calificación específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de doctores obtenida exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron doctores con la calificación especificada.")
    })
    @GetMapping("/doctores/calificacion")
    public ResponseEntity<List<Doctor>> listarDoctoresPorCalificacion(@RequestParam int calificacion) {
        List<Doctor> doctores = doctorService.buscarPorCalificacion(calificacion);
        if (doctores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(doctores);
        }
        return ResponseEntity.ok(doctores);
    }*/
    //Calificar doctor
    @Operation(summary = "Calificar a un doctor", description = "Permite a un usuario calificar a un doctor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Calificación registrada exitosamente."),
            @ApiResponse(responseCode = "404", description = "Doctor no encontrado."),
            @ApiResponse(responseCode = "400", description = "Calificación inválida.")
    })
    @PostMapping("/calificar/{doctorId}")
    public ResponseEntity<String> calificarDoctor(@PathVariable int doctorId, @RequestParam int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La calificación debe estar entre 1 y 5.");
        }

        Doctor doctor = doctorService.obtenerDoctorPorId(doctorId);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor no encontrado.");
        }

        doctorService.actualizarCalificacion(doctorId, calificacion);

        return ResponseEntity.ok("Calificación registrada exitosamente.");
    }

    //Busqueda de doctor por especialidad, profesional-nombre y/o rango de fechas y calificacion
    @GetMapping("/doctores/filtro")
    public ResponseEntity<List<Doctor>> listarDoctoresPorFiltros(
            @RequestParam(required = false) Integer idEspecialidad,
            @RequestParam(required = false) String nombreCompleto,
            @RequestParam(required = false) String horaInicio,
            @RequestParam(required = false) String horaFin,
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Integer calificacion) {

        List<Doctor> doctores = doctorService.buscarPorFiltros(idEspecialidad, nombreCompleto, horaInicio, horaFin, fecha, calificacion);
        return ResponseEntity.ok(doctores);
    }


    //Los 2 endpoints sirven para la pantalla del calendario, obtener los dias y disp del doctor
    @GetMapping("/doctores/{id}/disponibilidad")
    public ResponseEntity<List<LocalDate>> obtenerDiasConDisponibilidad(@PathVariable int idDoctor) {
        List<LocalDate> diasDisponibles = turnosService.obtenerDiasDisponibles(idDoctor);
        return ResponseEntity.ok(diasDisponibles);
    }

    //Este me traería las selecciones de los turnos disponibles para el doctor en la fecha seleccionada
    @GetMapping("/doctores/{id}/disponibilidad/{fecha}")
    public ResponseEntity<List<LocalTime>> obtenerHorariosDisponibles(
            @PathVariable int id,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<LocalTime> horarios = turnosService.obtenerHorariosDisponibles(id, fecha);
        return ResponseEntity.ok(horarios);
    }









}
