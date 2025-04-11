package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.ObrasSociales;


public interface obrasSociales {
    ObrasSociales getObraSocialById(Long id);
    void saveObraSocial(ObrasSociales obraSocial);
    void deleteObraSocial(Long id);
}