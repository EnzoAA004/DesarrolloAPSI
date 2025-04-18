package org.example.backgrupo3vima.Repository;

import org.example.backgrupo3vima.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String attr0);

    Optional<User> findByCorreo(String correo);
}
