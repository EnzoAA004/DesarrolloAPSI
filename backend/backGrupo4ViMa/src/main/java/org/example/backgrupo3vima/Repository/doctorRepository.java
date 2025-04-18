package org.example.backgrupo3vima.Repository;


import org.example.backgrupo3vima.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface doctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findByDisponibilidadAndFechaAndEstado(String horaInicio, String horaFin, String fechaInicio, String fechaFin, String estado);

    List<Doctor> findByNombreCompletoAndDisponibilidadAndFechaAndEstado(String nombreCompleto, String horaInicio, String horaFin, String fechaInicio, String fechaFin, String estado);

    List<Doctor> findByEspecialidadIdAndDisponibilidadAndFechaAndEstado(int idEspecialidad, String horaInicio, String horaFin, String fechaInicio, String fechaFin, String estado);

    List<Doctor> findByEspecialidadIdAndNombreCompletoAndDisponibilidadAndFechaAndEstado(int idEspecialidad, String nombreCompleto, String horaInicio, String horaFin, String fechaInicio, String fechaFin, String estado);


    List<Doctor> findByEspecialidadIdAndNombreCompletoContainingIgnoreCase(Integer especialidadId, String nombreCompleto);
    List<Doctor> findByEspecialidadId(Integer especialidadId);
    List<Doctor> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
}

