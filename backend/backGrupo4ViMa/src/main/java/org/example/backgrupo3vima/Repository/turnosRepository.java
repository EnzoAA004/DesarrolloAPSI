package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.TurnoEstado;
import org.example.backgrupo3vima.Entity.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface turnosRepository extends JpaRepository<Turnos, Integer> {


    @Query("SELECT COUNT(t) > 0 FROM Turnos t WHERE t.doctor.id = :doctorId AND t.fechaTurno BETWEEN :fechaInicio AND :fechaFin AND t.horaInicio <= :horaFin AND t.horaFin >= :horaInicio")
    boolean existsTurnoAsignado(@Param("doctorId") Integer doctorId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, @Param("horaInicio") Timestamp horaInicio, @Param("horaFin") Timestamp horaFin);

    List<Turnos> findByFechaTurnoAndEstado(LocalDate fechaTurno, TurnoEstado estado);

    @Query("SELECT t FROM Turnos t WHERE t.fechaTurno = :fechaTurno AND t.estado = :estado")
    List<Turnos> findByFechaTurnoAndEstado(@Param("fechaTurno") LocalDateTime fechaTurno, @Param("estado") TurnoEstado estado);
}
