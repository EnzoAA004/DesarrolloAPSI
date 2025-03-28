package Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class tokensEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String token;

    @Column
    private int id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private userEntity usuario;
}
