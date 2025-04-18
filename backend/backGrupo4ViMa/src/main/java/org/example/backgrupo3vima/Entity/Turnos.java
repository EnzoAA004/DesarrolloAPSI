package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
public class Turnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private LocalDate fechaTurno;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private User usuario;


    @ManyToOne
    @JoinColumn(name = "id_doctor", referencedColumnName = "id")
    private Doctor doctor;

    @Column
    private Timestamp horaInicio;

    @Column
    private Timestamp horaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TurnoEstado estado;

}
