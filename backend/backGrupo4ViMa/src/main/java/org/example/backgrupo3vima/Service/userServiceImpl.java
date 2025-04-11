package org.example.backgrupo3vima.Service;


import org.example.backgrupo3vima.Entity.User;
import org.example.backgrupo3vima.Repository.obrasSocialesRepository;
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
    private obrasSocialesRepository obrasSocialesRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User actualizarUsuario(Long id, Map<String, Object> updates) {
        return null;
    }

}
