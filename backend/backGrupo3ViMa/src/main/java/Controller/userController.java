package Controller;

import Entity.userEntity;
import Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usuario")

public class userController {

    @Autowired
    private userService userService;

    @GetMapping
    public ResponseEntity<List<userEntity>> todosLosUsuarios(){
        List<userEntity> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/{id}/nombre")
    public ResponseEntity<userEntity> actualizarNombre(@PathVariable Long id, @RequestBody String nombre) {
        userEntity usuario = userService.actualizarNombre(id, nombre);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/apellido")
    public ResponseEntity<userEntity> actualizarApellido(@PathVariable Long id, @RequestBody String apellido) {
        userEntity usuario = userService.actualizarApellido(id, apellido);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/edad")
    public ResponseEntity<userEntity> actualizarEdad(@PathVariable Long id, @RequestBody int edad) {
        userEntity usuario = userService.actualizarEdad(id, edad);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/fechaNacimiento")
    public ResponseEntity<userEntity> actualizarFechaNacimiento(@PathVariable Long id, @RequestBody String fechaNacimiento) {
        userEntity usuario = userService.actualizarFechaNacimiento(id, fechaNacimiento);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/genero")
    public ResponseEntity<userEntity> actualizarGenero(@PathVariable Long id, @RequestBody String genero) {
        userEntity usuario = userService.actualizarGenero(id, genero);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/obraSocial")
    public ResponseEntity<userEntity> actualizarObraSocial(@PathVariable Long id, @RequestBody String obraSocial) {
        userEntity usuario = userService.actualizarObraSocial(id, obraSocial);
        return ResponseEntity.ok(usuario);
    }


}
