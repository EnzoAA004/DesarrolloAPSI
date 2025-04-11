package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tokensRepository extends JpaRepository<Tokens, Long> {
    Optional<Tokens> findByUserId(Long userId);
}
