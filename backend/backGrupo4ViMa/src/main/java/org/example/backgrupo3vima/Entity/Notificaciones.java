package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;



//notificaciones al usuario
@Entity
@Data
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column
    private String logoNotificacion;

    @Column
    private String tipoNotificacion;

    @Column
    private String mensaje;

    @Column
    private String estado;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
