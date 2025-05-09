package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int idEspecialidad;

    @Column
    private String telefono;

    @Column
    private String correo;

    @Column
    private LocalTime horarioInicio;

    @Column
    private LocalTime horarioFin;

    @Column
    private String informacionAdicional;


    @Column(name = "calificacion_total", nullable = false)
    private int calificacionTotal = 0;

    @Column(name = "numero_calificaciones", nullable = false)
    private int numeroCalificaciones = 0;

    @Column(name = "calificacion_promedio", nullable = false)
    private int calificacionPromedio = 0;


    @Column(name = "activo", nullable = false)
    private boolean activo = true;




    @ManyToMany(mappedBy = "doctores")
    private List<User> usuarios;

    @ManyToOne
    @JoinColumn(name = "idEspecialidad", insertable = false, updatable = false)
    private Especialidad especialidad;
}
