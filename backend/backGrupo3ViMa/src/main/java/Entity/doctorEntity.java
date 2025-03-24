package Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class doctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String dni;

    private String email;

    private String especialidad;

    private String telefono;

}
