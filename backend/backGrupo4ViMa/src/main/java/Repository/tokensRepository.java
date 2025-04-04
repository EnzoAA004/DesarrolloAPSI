package Repository;

import Entity.tokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tokensRepository extends JpaRepository<tokensEntity, Long> {
    Optional<tokensEntity> findByUserId(Long userId);
}
