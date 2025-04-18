package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Repository.doctorRepository;
import org.example.backgrupo3vima.Repository.turnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class doctorServiceImpl implements doctorService {
    @Autowired
    private doctorRepository doctorRepository;
    @Autowired
    private turnosRepository turnosRepository;


    @Override
    public List<Doctor> todosLosDoctores() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> buscarPorEspecialidadYNombre(Integer idEspecialidad, String nombreCompleto, String horaInicio, String horaFin, String fechaInicio, String fechaFin) {
        List<Doctor> doctores = new ArrayList<>();

        // Normaliza el valor del nombreCompleto para tratar cadenas vacías como nulas
        if (nombreCompleto != null && nombreCompleto.trim().isEmpty()) {
            nombreCompleto = null;
        }

        // Si todos los filtros están presentes, realiza la conjunción (AND)
        if (idEspecialidad != null && nombreCompleto != null && horaInicio != null && horaFin != null && fechaInicio != null && fechaFin != null) {
            doctores = doctorRepository.findByEspecialidadIdAndNombreCompletoAndDisponibilidadAndFechaAndEstado(idEspecialidad, nombreCompleto, horaInicio, horaFin, fechaInicio, fechaFin, "CANCELADO");
        } else if (idEspecialidad != null && horaInicio != null && horaFin != null && fechaInicio != null && fechaFin != null) {
            doctores = doctorRepository.findByEspecialidadIdAndDisponibilidadAndFechaAndEstado(idEspecialidad, horaInicio, horaFin, fechaInicio, fechaFin, "CANCELADO");
        } else if (nombreCompleto != null && horaInicio != null && horaFin != null && fechaInicio != null && fechaFin != null) {
            doctores = doctorRepository.findByNombreCompletoAndDisponibilidadAndFechaAndEstado(nombreCompleto, horaInicio, horaFin, fechaInicio, fechaFin, "CANCELADO");
        } else if (horaInicio != null && horaFin != null && fechaInicio != null && fechaFin != null) {
            doctores = doctorRepository.findByDisponibilidadAndFechaAndEstado(horaInicio, horaFin, fechaInicio, fechaFin, "CANCELADO");
        } else if (idEspecialidad != null && nombreCompleto != null) {
            doctores = doctorRepository.findByEspecialidadIdAndNombreCompletoContainingIgnoreCase(idEspecialidad, nombreCompleto);
        } else if (idEspecialidad != null) {
            doctores = doctorRepository.findByEspecialidadId(idEspecialidad);
        } else if (nombreCompleto != null) {
            doctores = doctorRepository.findByNombreCompletoContainingIgnoreCase(nombreCompleto);
        }
        return doctores;
    }

    @Override
    public boolean verificarDisponibilidadTurno(Integer doctorId, String fechaInicio, String fechaFin, String horaInicio, String horaFin) {
        LocalDate fechaInicioParsed = LocalDate.parse(fechaInicio);
        LocalDate fechaFinParsed = LocalDate.parse(fechaFin);
        java.sql.Timestamp horaInicioParsed = Timestamp.valueOf(horaInicio);
        java.sql.Timestamp horaFinParsed = Timestamp.valueOf(horaFin);

        return !turnosRepository.existsTurnoAsignado(doctorId, fechaInicioParsed, fechaFinParsed, horaInicioParsed, horaFinParsed);
    }

    @Override
    public Doctor obtenerDoctorPorId(Integer doctorId) {
        return doctorRepository.findById(doctorId).orElse(null);
    }
}
