package org.example.backgrupo3vima.Entity.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contrasenia;
    private String token;
}
