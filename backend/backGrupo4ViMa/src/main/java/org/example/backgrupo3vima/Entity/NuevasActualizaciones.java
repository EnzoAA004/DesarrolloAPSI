package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;



//Actualizaciones generales - opcional agregar para todos los users -
@Entity
@Data
public class NuevasActualizaciones {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column
    private String imagen;

    @Column
    private String descripcion;
}
