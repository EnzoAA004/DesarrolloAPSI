package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

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


}
