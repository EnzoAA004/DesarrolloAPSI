package org.example.backgrupo3vima.Entity.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class TurnoDTO {
    private Integer doctorId;
    private Integer usuarioId;
    private LocalDate fechaTurno;
    private Timestamp horaInicio;
    private Timestamp horaFin;
}
