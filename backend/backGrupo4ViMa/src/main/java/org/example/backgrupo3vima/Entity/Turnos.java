package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "turnos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_doctor", "fechaTurno", "horaInicio", "horaFin"})
})
public class Turnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDate fechaTurno;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private User usuario;

    @Column
    private String nombrePaciente;

    @Column
    private String apellidoPaciente;

    @Column
    private String edadPaciente;

    @Column
    private String explicacionPaciente;

    @ManyToOne
    @JoinColumn(name = "id_doctor", referencedColumnName = "id")
    private Doctor doctor;

    @Column
    private LocalTime horaInicio;

    @Column
    private LocalTime horaFin;

    @Column
    private String nota;

    @Column
    private String archivoAdjunto;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TurnoEstado estado;

}
