package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Entity.TurnoEstado;
import org.example.backgrupo3vima.Entity.Turnos;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Entity.dto.TurnoDTO;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.example.backgrupo3vima.Service.turnosService;
import org.example.backgrupo3vima.Service.doctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turnos")
public class turnosController {
    @Autowired
    private turnosService turnosService;

    @Autowired
    private turnosRepository turnosRepository;

    @Autowired
    private doctorService doctorService;


    @PostMapping("/crearTurno")
    public ResponseEntity<String> crearTurno(@RequestBody TurnoDTO turnoDTO) {

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

        // Crear y guardar el nuevo turno en la base de datos
        Turnos nuevoTurno = new Turnos();

        nuevoTurno.setEstado(TurnoEstado.PENDIENTE);

        Doctor doctor = doctorService.obtenerDoctorPorId(turnoDTO.getDoctorId());
        User usuario = turnosService.obtenerUsuarioPorId(turnoDTO.getUsuarioId());
        nuevoTurno.setDoctor(doctor);
        nuevoTurno.setUsuario(usuario);

        nuevoTurno.setFechaTurno(turnoDTO.getFechaTurno());

        nuevoTurno.setHoraInicio(new java.sql.Timestamp(turnoDTO.getHoraInicio().getTime()));
        nuevoTurno.setHoraFin(new java.sql.Timestamp(turnoDTO.getHoraFin().getTime()));

        turnosRepository.save(nuevoTurno);

        return ResponseEntity.ok("Turno confirmado exitosamente.");
    }

    @PutMapping("/confirmarTurno/{id}")
    public ResponseEntity<String> confirmarTurno(@PathVariable int id) {
        Turnos turno = turnosRepository.findById(id).orElse(null);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado.");
        }

        turno.setEstado(TurnoEstado.CONFIRMADO);
        turnosRepository.save(turno);

        return ResponseEntity.ok("Turno confirmado exitosamente.");
    }

    @PutMapping("/cancelarTurno/{id}")
    public ResponseEntity<String> cancelarTurno(@PathVariable int id) {
        Turnos turno = turnosRepository.findById(id).orElse(null);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado.");
        }

        turno.setEstado(TurnoEstado.CANCELADO);
        turnosRepository.save(turno);

        return ResponseEntity.ok("Turno cancelado exitosamente.");
    }

}
