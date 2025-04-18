package org.example.backgrupo3vima.Service;


import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.example.backgrupo3vima.Entity.Tokens;
import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Repository.obrasSocialesRepository;
import org.example.backgrupo3vima.Repository.tokensRepository;
import org.example.backgrupo3vima.Repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private userService userService;

    @Autowired
    private tokensService tokensService;

    @Autowired
    private obrasSocialesRepository obrasSocialesRepository;

    @Autowired
    private tokensRepository tokensRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User actualizarUsuario(Long id, Map<String, Object> updates) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        updates.forEach((key, value) -> {
            if (value == null) {
                throw new IllegalArgumentException("El valor para el campo '" + key + "' no puede ser nulo");
            }
            switch (key) {
                case "correo_usuario":
                    existingUser.setCorreo((String) value);
                    break;
                case "nombre_usuario":
                    existingUser.setNombre((String) value);
                    break;
                case "apellido_usuario":
                    existingUser.setApellido((String) value);
                    break;
                case "edad":
                    existingUser.setEdad(String.valueOf((Integer) value));
                    break;
                case "id_obra_social":
                    ObrasSociales obraSocial = getObraSocialById((Integer) value);
                    existingUser.setObraSocial(obraSocial);
                    break;
                default:
                    throw new IllegalArgumentException("Campo no válido: " + key);
            }
        });

        return userRepository.save(existingUser);
    }

    @Override
    public User crearUsuario(User user) {
        if (userRepository.existsByEmail((user.getCorreo()))) {
            throw new RuntimeException("El email ya está registrado");
        }
        return userRepository.save(user);
    }

    @Override
    public ObrasSociales getObraSocialById(Integer idObraSocial) {
        return obrasSocialesRepository.findById(Long.valueOf(idObraSocial))
                .orElseThrow(() -> new RuntimeException("Obra social no encontrada"));
    }


    @Override
    public String generarTokenDeCambioContrasenia(String correo) {
        User usuario = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar el token
        String token = String.valueOf(tokensService.crearTokenParaUsuario((long) usuario.getId()));

        // Enviar el token por correo
        String asunto = "Solicitud de cambio de contraseña";
        String mensaje = "Tu token para cambiar la contraseña es: " + token;
        tokensService.enviarCorreo(usuario.getCorreo(), asunto, mensaje);

        return token;
    }


    @Override
    public void actualizarContrasenia(String correo, String nuevaContrasenia) {
        User usuario = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setContrasenia(nuevaContrasenia);
        userRepository.save(usuario);
    }


}
