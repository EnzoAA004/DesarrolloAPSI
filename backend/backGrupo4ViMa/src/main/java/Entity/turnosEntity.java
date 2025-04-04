package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class turnosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String idUsuario;

    @Column
    private LocalDate fechaTurno;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private userEntity usuario;


}
