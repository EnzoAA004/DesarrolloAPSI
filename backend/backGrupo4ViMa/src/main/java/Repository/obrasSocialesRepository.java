package Repository;

import Entity.obrasSocialesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface obrasSocialesRepository extends JpaRepository<obrasSocialesEntity, Long> {
}
