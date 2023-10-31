package tn.moatez.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.moatez.project.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
