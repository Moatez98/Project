package tn.moatez.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.moatez.project.model.TokenPwdRest;

@Repository
public interface TokenPwdRestRepository extends JpaRepository<TokenPwdRest,Long> {
    boolean existsByUser_Email(String email);
    TokenPwdRest findTokenPwdRestByUser_Email(String email);
}

