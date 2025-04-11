package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.ObrasSociales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface obrasSocialesRepository extends JpaRepository<ObrasSociales, Long> {
}
