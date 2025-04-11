package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.Turnos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface turnosRepository extends JpaRepository<Turnos, Integer> {
}
