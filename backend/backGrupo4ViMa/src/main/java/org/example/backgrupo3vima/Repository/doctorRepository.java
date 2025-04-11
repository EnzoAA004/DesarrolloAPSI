package org.example.backgrupo3vima.Repository;


import org.example.backgrupo3vima.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface doctorRepository extends JpaRepository<Doctor, Integer> {
}
