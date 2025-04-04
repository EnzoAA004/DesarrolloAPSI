package Repository;


import Entity.doctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface doctorRepository extends JpaRepository<doctorEntity, Integer> {
}
