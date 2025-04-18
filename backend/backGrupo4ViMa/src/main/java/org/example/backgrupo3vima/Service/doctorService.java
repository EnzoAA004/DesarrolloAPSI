package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.Doctor;

import java.util.List;

public interface doctorService {
    List<Doctor> todosLosDoctores();

    //esta es la busqueda para turnos que tengan estas caracteristicas
    List<Doctor> buscarPorEspecialidadYNombre(Integer idEspecialidad, String nombreCompleto, String horaInicio, String horaFin, String fechaInicio, String fechaFin);

    boolean verificarDisponibilidadTurno(Integer doctorId, String fechaInicio, String fechaFin, String horaInicio, String horaFin);

    Doctor obtenerDoctorPorId(Integer doctorId);
}
