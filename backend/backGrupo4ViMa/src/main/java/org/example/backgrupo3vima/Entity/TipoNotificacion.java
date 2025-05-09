package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;


//extiende la entidad notificaciones para tener el tipo de notificacion
@Entity
@Data
public class TipoNotificacion {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column
    private String logoNotificacion;

    @Column
    private int idtipoNotificacion;

    @Column
    private String mensaje;

    @Column
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private User usuario;

}

