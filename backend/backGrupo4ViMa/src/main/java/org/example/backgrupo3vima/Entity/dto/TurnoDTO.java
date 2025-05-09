package org.example.backgrupo3vima.Entity.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoDTO {
    private Integer doctorId;
    private Integer usuarioId;
    private LocalDate fechaTurno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
