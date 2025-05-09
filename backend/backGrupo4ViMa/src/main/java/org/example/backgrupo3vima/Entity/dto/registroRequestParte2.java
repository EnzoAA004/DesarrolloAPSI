package org.example.backgrupo3vima.Entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class registroRequestParte2 {
    @NotEmpty(message = "El campo 'dni' es obligatorio.")
    private String dni;

    @NotNull(message = "El campo 'fechaNacimiento' es obligatorio.")
    private String fechaNacimiento;

    @NotNull(message = "El campo 'edad' es obligatorio.")
    private Integer edad;

    @NotEmpty(message = "El campo 'genero' es obligatorio.")
    private String genero;

    @NotNull(message = "El campo 'idObraSocial' es obligatorio.")
    private Long idObraSocial;

    @NotEmpty(message = "El campo 'Celular' es obligatorio.")
    private String celular;
}
