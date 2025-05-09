package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.example.backgrupo3vima.Repository.obrasSocialesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class obrasSocialesImpl implements obrasSociales {

    @Autowired
    private obrasSocialesRepository obrasSocialesRepository;

    public obrasSocialesImpl(obrasSocialesRepository obrasSocialesRepository) {
        this.obrasSocialesRepository = obrasSocialesRepository;
    }

    @Override
    public ObrasSociales getObraSocialById(Long id) {
        return obrasSocialesRepository.findById(id).orElse(null);
    }

    @Override
    public void saveObraSocial(ObrasSociales obraSocial) {
        obrasSocialesRepository.save(obraSocial);
    }

    @Override
    public void deleteObraSocial(Long id) {
        obrasSocialesRepository.deleteById(id);
    }

    @Override
    public List<ObrasSociales> todasLasObrasSociales() {
        return obrasSocialesRepository.findAll();
    }
}