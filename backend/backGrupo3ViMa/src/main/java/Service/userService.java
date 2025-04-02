package Service;

import Entity.userEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface userService {
    List<userEntity> getAllUsers();
    userEntity actualizarNombre(Long id, String nombre);
    userEntity actualizarApellido(Long id, String apellido);
    userEntity actualizarEdad(Long id, int edad);
    userEntity actualizarFechaNacimiento(Long id, String fechaNacimiento);
    userEntity actualizarGenero(Long id, String genero);
    userEntity actualizarObraSocial(Long id, String obraSocial);

}
