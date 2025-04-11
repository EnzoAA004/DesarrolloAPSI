package org.example.backgrupo3vima.Controller;

import org.example.backgrupo3vima.Entity.User;
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

    @GetMapping
    public ResponseEntity<List<User>> todosLosUsuarios(){
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User usuario = userService.actualizarUsuario(id, updates);
        return ResponseEntity.ok(usuario);
    }

}
