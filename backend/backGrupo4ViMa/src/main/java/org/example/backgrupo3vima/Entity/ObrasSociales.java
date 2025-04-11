package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "obras_sociales")
public class ObrasSociales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ObraSocialType tipoObraSocial;

    @OneToMany(mappedBy = "obraSocial")
    private List<User> usuarios;

    public ObrasSociales() {
    }
}