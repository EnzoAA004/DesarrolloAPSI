package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.User;

import java.util.List;
import java.util.Map;


public interface userService {
    List<User> getAllUsers();

    User actualizarUsuario(Long id, Map<String, Object> updates);
}
