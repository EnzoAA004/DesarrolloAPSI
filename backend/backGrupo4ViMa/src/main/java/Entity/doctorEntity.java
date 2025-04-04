package Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class doctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String apellido;

    private String especialidad;

    private String telefono;

    private String email;

    private Timestamp horarioInicio;

    private Timestamp horarioFin;

    @ManyToMany(mappedBy = "doctores")
    private List<userEntity> usuarios;
}
