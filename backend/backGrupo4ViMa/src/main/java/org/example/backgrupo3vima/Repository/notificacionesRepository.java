package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notificacionesRepository extends JpaRepository<Notificaciones, Integer> {
}
