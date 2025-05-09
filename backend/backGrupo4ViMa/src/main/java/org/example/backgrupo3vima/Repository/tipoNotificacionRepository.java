package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tipoNotificacionRepository extends JpaRepository<TipoNotificacion, Integer> {
}
