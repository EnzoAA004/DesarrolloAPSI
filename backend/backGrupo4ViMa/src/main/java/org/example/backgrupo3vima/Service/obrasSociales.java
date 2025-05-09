package org.example.backgrupo3vima.Service;

import org.example.backgrupo3vima.Entity.ObrasSociales;

import java.util.List;


public interface obrasSociales {
    ObrasSociales getObraSocialById(Long id);
    void saveObraSocial(ObrasSociales obraSocial);
    void deleteObraSocial(Long id);

    List<ObrasSociales> todasLasObrasSociales();
}