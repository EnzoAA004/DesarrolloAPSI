package org.example.backgrupo3vima.Entity.dto;

import lombok.Data;

@Data
public class ValidarTokenRequest {
    private String correo;
    private String tokenIngresado;
}
