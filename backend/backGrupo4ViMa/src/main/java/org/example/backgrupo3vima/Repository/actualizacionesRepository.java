package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.NuevasActualizaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface actualizacionesRepository extends JpaRepository<NuevasActualizaciones, Integer> {
}
