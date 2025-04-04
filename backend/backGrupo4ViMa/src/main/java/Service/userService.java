package Service;

import Entity.userEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface userService {
    List<userEntity> getAllUsers();

    userEntity actualizarUsuario(Long id, Map<String, Object> updates);
}
