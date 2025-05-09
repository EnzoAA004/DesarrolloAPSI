package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(unique = true)
    private String dni;

    @Column(unique = true)
    private String correo;

    @Column
    private String genero;

    @Column
    private String edad;

    @Column
    private LocalDate fechaNacimiento;

    @Column(length = 15, nullable = false)
    private String celular;

    @Column
    private String contrasenia;

    @Column
    private String urlimagenperfil;

    @ManyToOne
    @JoinColumn(name = "obra_social_id")
    private ObrasSociales obraSocial;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column
    private Boolean temporal; //Estado del usuario a la hora del ingreso

    @Column
    private Boolean notificacionesActivadas;

    @Column
    private Boolean vibracionActivada;

    @Column
    private Boolean actualizacionesApp;

    @Column
    private Boolean modoOscuro;


    //2 columnas para la parte de inicio y cerrar sesion
    @Column
    private LocalDateTime ultimaActividad; // Fecha y hora de la última actividad del usuario

    @Column
    private Boolean sesionActiva; // Indica si el usuario tiene la sesión iniciada

    @ManyToMany
    @JoinTable(
            name = "usuario_doctor",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctores;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Turnos> turnos;

}
