package org.example.backgrupo3vima.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descripcion;

    @OneToMany(mappedBy = "especialidad")
    private List<Doctor> doctores;
}
