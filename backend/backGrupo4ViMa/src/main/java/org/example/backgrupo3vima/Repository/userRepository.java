package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<userEntity, Long> {
    Optional<userEntity> findByEmail(String email);
}
