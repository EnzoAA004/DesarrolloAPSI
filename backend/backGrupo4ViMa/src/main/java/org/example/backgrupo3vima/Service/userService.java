package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.example.backgrupo3vima.Entity.User;

import java.util.List;
import java.util.Map;


public interface userService {
    List<User> getAllUsers();

    //User actualizarUsuario(Long id, Map<String, Object> updates);

    User crearUsuario(User user);

    ObrasSociales getObraSocialById(Integer idObraSocial);


    String generarTokenDeCambioContrasenia(String correo);

    void actualizarContrasenia(String correo, String nuevaContrasenia);

    boolean validarSesionActiva(String correo);

    User actualizarUsuarioPorCorreo(String correo, Map<String, Object> updates);

    User obtenerPorId(Long userId);

    User guardarUsuarioTemporal(User user);

    User obtenerUsuarioTemporalPorId(Long usuarioId);

    User finalizarRegistroUsuario(User user);

    void guardarUsuario(User user);

    void eliminarUsuarioPorId(Long usuarioId);
}
