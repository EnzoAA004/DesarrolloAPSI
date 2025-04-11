package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.tokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tokensRepository extends JpaRepository<tokensEntity, Long> {
    Optional<tokensEntity> findByUser(userRepository user);
}
