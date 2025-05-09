package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Doctor;
import org.example.backgrupo3vima.Entity.dto.DoctorDTO;

import java.util.List;

public interface doctorService {
    List<Doctor> todosLosDoctores();

    void crearDoctor(DoctorDTO doctorDTO);

    void actualizarDoctor(Doctor doctor);

    boolean verificarDisponibilidadTurno(Integer doctorId, String fechaInicio, String fechaFin, String horaInicio, String horaFin);

    Doctor obtenerDoctorPorId(Integer doctorId);

    void actualizarCalificacion(int doctorId, int calificacion);

    List<Doctor> buscarPorFiltros(Integer idEspecialidad, String nombreCompleto, String horaInicio, String horaFin, String fecha, Integer calificacion);


}
