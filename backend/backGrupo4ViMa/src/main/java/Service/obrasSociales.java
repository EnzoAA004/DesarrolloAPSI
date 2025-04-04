package Service;

import Entity.obrasSocialesEntity;

@Service
public interface obrasSociales {
    obrasSocialesEntity getObraSocialById(Long id);
    void saveObraSocial(obrasSocialesEntity obraSocial);
    void deleteObraSocial(Long id);
}