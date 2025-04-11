package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.userEntity;

import java.util.List;
import java.util.Map;


public interface userService {
    List<userEntity> getAllUsers();

    userEntity actualizarUsuario(Long id, Map<String, Object> updates);

    userEntity actualizarNombre(Long id, String nombre);

    userEntity actualizarApellido(Long id, String apellido);

    userEntity actualizarEdad(Long id, int edad);

    userEntity actualizarFechaNacimiento(Long id, String fechaNacimiento);

    userEntity actualizarGenero(Long id, String genero);

    userEntity actualizarObraSocial(Long id, String obraSocialId);
}
