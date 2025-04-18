package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Entity.dto.UserRequest;
import org.example.backgrupo3vima.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Usuario")

public class userController {

    @Autowired
    private userService userService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<User>> todosLosUsuarios(){
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> crearUsuario(@RequestBody UserRequest userRequest) {
        // Validar que todos los campos tengan información
        StringBuilder errores = new StringBuilder();
        if (userRequest.getCorreo() == null || userRequest.getCorreo().isEmpty()) errores.append("El campo 'correo' es obligatorio. ");
        if (userRequest.getDni() == null || userRequest.getDni().isEmpty()) errores.append("El campo 'dni' es obligatorio. ");
        if (userRequest.getNombre() == null || userRequest.getNombre().isEmpty()) errores.append("El campo 'nombre' es obligatorio. ");
        if (userRequest.getApellido() == null || userRequest.getApellido().isEmpty()) errores.append("El campo 'apellido' es obligatorio. ");
        if (userRequest.getContrasenia() == null || userRequest.getContrasenia().isEmpty()) errores.append("El campo 'contrasenia' es obligatorio. ");
        if (userRequest.getEdad() == null) errores.append("El campo 'edad' es obligatorio. ");
        if (userRequest.getFechaNacimiento() == null) errores.append("El campo 'fechaNacimiento' es obligatorio. ");
        if (userRequest.getIdObraSocial() == null) errores.append("El campo 'idObraSocial' es obligatorio. ");

        if (errores.length() > 0) {
            return ResponseEntity.badRequest().body(Map.of("errores", errores.toString().trim()));
        }

        User user = new User();
        user.setCorreo(userRequest.getCorreo());
        user.setDni(userRequest.getDni());
        user.setNombre(userRequest.getNombre());
        user.setApellido(userRequest.getApellido());
        user.setContrasenia(userRequest.getContrasenia());
        user.setEdad(userRequest.getEdad());
        user.setGenero(userRequest.getGenero()); // M o F para masculino o femenino. Luego O para otro.
        user.setFechaNacimiento(userRequest.getFechaNacimiento());

        // Buscar la obra social por ID
        ObrasSociales obraSocial = userService.getObraSocialById(userRequest.getIdObraSocial());
        if (obraSocial == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se encontró la obra social con el ID proporcionado."));
        }
        user.setObraSocial(obraSocial);

        User nuevoUsuario = userService.crearUsuario(user);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario creado exitosamente. Ahora ve a iniciar sesión.", "usuario", nuevoUsuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User usuario = userService.actualizarUsuario(id, updates);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/solicitarCambioContrasenia") //Ingreso el correo para que me mande un mail con el token.
    public ResponseEntity<?> solicitarCambioContrasenia(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");

        if (correo == null || correo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo es obligatorio."));
        }

        try {
            String token = userService.generarTokenDeCambioContrasenia(correo);
            return ResponseEntity.ok(Map.of("mensaje", "Token generado exitosamente.", "token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/cambiarContrasenia") // Recibe el correo y la nueva contraseña.
    public ResponseEntity<?> cambiarContrasenia(@RequestBody Map<String, String> datos) {
        String correo = datos.get("correo");
        String nuevaContrasenia = datos.get("nuevaContrasenia");

        if (correo == null || correo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo es obligatorio."));
        }

        if (nuevaContrasenia == null || nuevaContrasenia.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "La nueva contraseña es obligatoria."));
        }

        try {
            userService.actualizarContrasenia(correo, nuevaContrasenia); // Método para actualizar la contraseña.
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada exitosamente."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }





}


