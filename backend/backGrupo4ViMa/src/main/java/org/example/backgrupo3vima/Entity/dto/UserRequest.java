package org.example.backgrupo3vima.Entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String correo;
    private String dni;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private String edad;
    private String genero;
    private LocalDate fechaNacimiento;
    private Integer idObraSocial;
}
