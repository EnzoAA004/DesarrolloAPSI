package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.obrasSocialesEntity;
import org.springframework.stereotype.Service;

@Service
public interface obrasSociales {
    obrasSocialesEntity getObraSocialById(Long id);
    void saveObraSocial(obrasSocialesEntity obraSocial);
    void deleteObraSocial(Long id);
}