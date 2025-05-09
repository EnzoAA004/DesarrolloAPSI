package org.example.backgrupo3vima.Entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class registroRequestParte1 {
    @NotEmpty(message = "El campo 'correo' es obligatorio.")
    private String correo;

    @NotEmpty(message = "El campo 'nombre' es obligatorio.")
    private String nombre;

    @NotEmpty(message = "El campo 'apellido' es obligatorio.")
    private String apellido;

    @NotEmpty(message = "El campo 'contrasenia' es obligatorio.")
    private String contrasenia;

    @NotEmpty(message = "El campo 'repetirContrasenia' es obligatorio.")
    private String repetirContrasenia;
}



