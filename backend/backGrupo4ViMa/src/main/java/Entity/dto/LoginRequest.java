package Entity.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String contrasenia;
    private String token;
}
