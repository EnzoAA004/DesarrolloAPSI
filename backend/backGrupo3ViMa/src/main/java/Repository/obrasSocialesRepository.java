package Repository;

import Service.obrasSociales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface obrasSocialesRepository extends JpaRepository<obrasSociales, Integer> {
}
