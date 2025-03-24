package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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

    @Column
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
    private int obraSocial;

    @Column
    private String contrasenia;
}
