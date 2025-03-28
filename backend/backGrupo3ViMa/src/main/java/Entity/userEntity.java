package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class userEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(unique = true)
    private String dni;

    @Column
    private String email;

    @Column
    private String genero;

    @Column
    private Integer edad;

    @Column
    private LocalDate fechaNacimiento;

    @Column
    private String contrasenia;

    @Column
    private String urlimagenperfil;

    @ManyToOne
    @JoinColumn(name = "obra_social_id")
    private obrasSocialesEntity obraSocial;


    @ManyToMany
    @JoinTable(
            name = "usuario_doctor",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<doctorEntity> doctores;
}
