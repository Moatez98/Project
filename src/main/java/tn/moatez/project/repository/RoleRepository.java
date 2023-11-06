package tn.moatez.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.moatez.project.enums.ERole;
import tn.moatez.project.model.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Set<Role> findRolesByName(ERole name);
}
