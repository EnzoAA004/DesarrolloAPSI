package Controller;

import Entity.userEntity;
import Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PatchMapping("/{id}")
    public ResponseEntity<userEntity> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        userEntity usuario = userService.actualizarUsuario(id, updates);
        return ResponseEntity.ok(usuario);
    }

}
