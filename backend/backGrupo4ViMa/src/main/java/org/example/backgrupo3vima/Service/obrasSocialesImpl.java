package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.obrasSocialesEntity;
import org.example.backgrupo3vima.Repository.obrasSocialesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class obrasSocialesImpl implements obrasSociales {

    @Autowired
    private obrasSocialesRepository obrasSocialesRepository;

    public obrasSocialesImpl(obrasSocialesRepository obrasSocialesRepository) {
        this.obrasSocialesRepository = obrasSocialesRepository;
    }

    @Override
    public obrasSocialesEntity getObraSocialById(Long id) {
        return obrasSocialesRepository.findById(id).orElse(null);
    }

    @Override
    public void saveObraSocial(obrasSocialesEntity obraSocial) {
        obrasSocialesRepository.save(obraSocial);
    }

    @Override
    public void deleteObraSocial(Long id) {
        obrasSocialesRepository.deleteById(id);
    }
}