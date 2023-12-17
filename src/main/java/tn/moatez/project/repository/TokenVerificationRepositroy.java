package tn.moatez.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.moatez.project.model.TokenVerification;
import tn.moatez.project.model.User;
@Repository
public interface TokenVerificationRepositroy extends JpaRepository<TokenVerification,Long> {
    TokenVerification findByUser_Email(String email);
    boolean existsByUser_Email(String email);
}
