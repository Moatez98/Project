package tn.moatez.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.moatez.project.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phonenumber);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String name);
}
