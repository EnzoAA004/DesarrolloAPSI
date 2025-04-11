package org.example.backgrupo3vima.Service;


import org.example.backgrupo3vima.Entity.obrasSocialesEntity;
import org.example.backgrupo3vima.Entity.userEntity;
import org.example.backgrupo3vima.Repository.obrasSocialesRepository;
import org.example.backgrupo3vima.Repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private obrasSocialesRepository obrasSocialesRepository;

    @Override
    public List<userEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public userEntity actualizarUsuario(Long id, Map<String, Object> updates) {
        return null;
    }

    @Override
    public userEntity actualizarNombre(Long id, String nombre) {
        userEntity usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(nombre);
        return userRepository.save(usuario);
    }

    @Override
    public userEntity actualizarApellido(Long id, String apellido) {
        userEntity usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setApellido(apellido);
        return userRepository.save(usuario);
    }

    @Override
    public userEntity actualizarEdad(Long id, int edad) {
        userEntity usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEdad(edad);
        return userRepository.save(usuario);
    }

    @Override
    public userEntity actualizarFechaNacimiento(Long id, String fechaNacimiento) {
        userEntity usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        return userRepository.save(usuario);
    }

    @Override
    public userEntity actualizarGenero(Long id, String genero) {
        userEntity usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setGenero(genero);
        return userRepository.save(usuario);
    }

    @Override
    public userEntity actualizarObraSocial(Long id, String obraSocialId) {
        userEntity usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        obrasSocialesEntity obraSocial = (obrasSocialesEntity) obrasSocialesRepository.findById((long) Long.parseLong(obraSocialId)).orElseThrow(() -> new RuntimeException("Obra social no encontrada"));
        usuario.setObraSocial(obraSocial);
        return userRepository.save(usuario);
    }
}
