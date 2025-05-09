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

    // Por disponibilidad general
    @Query("SELECT d FROM Doctor d JOIN d.usuarios u JOIN u.turnos t WHERE " +
            "d.horarioInicio >= :horaInicio AND d.horarioFin <= :horaFin AND " +
            "t.fechaTurno = :fecha AND t.estado = :estado")
    List<Doctor> findByDisponibilidadAndFechaAndEstado(
            @Param("horaInicio") Timestamp horaInicio,
            @Param("horaFin") Timestamp horaFin,
            @Param("fecha") LocalDate fecha,
            @Param("estado") String estado
    );

    // Especialidad + Nombre + Disponibilidad + Fecha + Estado
    @Query("SELECT d FROM Doctor d JOIN d.usuarios u JOIN u.turnos t WHERE " +
            "d.especialidad.id = :idEspecialidad AND " +
            "LOWER(CONCAT(d.nombre, ' ', d.apellido)) LIKE LOWER(CONCAT('%', :nombreCompleto, '%')) AND " +
            "d.horarioInicio >= :horaInicio AND d.horarioFin <= :horaFin AND " +
            "t.fechaTurno = :fecha AND t.estado = :estado")
    List<Doctor> buscarPorEspecialidadYNombreCompletoYDisponibilidad(
            @Param("idEspecialidad") int idEspecialidad,
            @Param("nombreCompleto") String nombreCompleto,
            @Param("horaInicio") Timestamp horaInicio,
            @Param("horaFin") Timestamp horaFin,
            @Param("fecha") LocalDate fecha,
            @Param("estado") String estado
    );

    // Solo especialidad + disponibilidad
    @Query("SELECT d FROM Doctor d JOIN d.usuarios u JOIN u.turnos t WHERE " +
            "d.especialidad.id = :idEspecialidad AND " +
            "d.horarioInicio >= :horaInicio AND d.horarioFin <= :horaFin AND " +
            "t.fechaTurno = :fecha AND t.estado = :estado")
    List<Doctor> buscarPorEspecialidadYDisponibilidad(
            @Param("idEspecialidad") int idEspecialidad,
            @Param("horaInicio") Timestamp horaInicio,
            @Param("horaFin") Timestamp horaFin,
            @Param("fecha") LocalDate fecha,
            @Param("estado") String estado
    );

    // Solo nombre + disponibilidad
    @Query("SELECT d FROM Doctor d JOIN d.usuarios u JOIN u.turnos t WHERE " +
            "LOWER(CONCAT(d.nombre, ' ', d.apellido)) LIKE LOWER(CONCAT('%', :nombreCompleto, '%')) AND " +
            "d.horarioInicio >= :horaInicio AND d.horarioFin <= :horaFin AND " +
            "t.fechaTurno = :fecha AND t.estado = :estado")
    List<Doctor> buscarPorNombreCompletoYDisponibilidad(
            @Param("nombreCompleto") String nombreCompleto,
            @Param("horaInicio") Timestamp horaInicio,
            @Param("horaFin") Timestamp horaFin,
            @Param("fecha") LocalDate fecha,
            @Param("estado") String estado
    );

    // Solo por especialidad
    List<Doctor> findByEspecialidadId(Integer especialidadId);

    // Solo por nombre
    @Query("SELECT d FROM Doctor d WHERE LOWER(CONCAT(d.nombre, ' ', d.apellido)) LIKE LOWER(CONCAT('%', :nombreCompleto, '%'))")
    List<Doctor> buscarPorNombreCompleto(@Param("nombreCompleto") String nombreCompleto);

    // Nombre y especialidad
    @Query("SELECT d FROM Doctor d WHERE d.especialidad.id = :especialidadId AND LOWER(CONCAT(d.nombre, ' ', d.apellido)) LIKE LOWER(CONCAT('%', :nombreCompleto, '%'))")
    List<Doctor> buscarPorEspecialidadYNombreCompleto(
            @Param("especialidadId") Integer especialidadId,
            @Param("nombreCompleto") String nombreCompleto
    );
}
