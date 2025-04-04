package Repository;

import Entity.turnosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface turnosRepository extends JpaRepository<turnosEntity, Integer> {
}
