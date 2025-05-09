package org.example.backgrupo3vima.Entity.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private String nombre;
    private String apellido;
    private int idEspecialidad;
    private String telefono;
    private String correo;
    private String horarioInicio; // Formato: "yyyy-MM-dd HH:mm:ss"
    private String horarioFin;    // Formato: "yyyy-MM-dd HH:mm:ss"
    private String informacionAdicional;
    private boolean eliminado;
}
