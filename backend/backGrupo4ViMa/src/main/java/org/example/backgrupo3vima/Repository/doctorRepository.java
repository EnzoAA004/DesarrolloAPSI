package org.example.backgrupo3vima.Repository;


import org.example.backgrupo3vima.Entity.doctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface doctorRepository extends JpaRepository<doctorEntity, Integer> {
}
