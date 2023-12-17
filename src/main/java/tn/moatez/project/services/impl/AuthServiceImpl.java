package tn.moatez.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.moatez.project.model.TokenPwdRest;
import tn.moatez.project.model.TokenVerification;
import tn.moatez.project.model.User;
import tn.moatez.project.payload.request.ChangePwdVerifTokenRequest;
import tn.moatez.project.repository.TokenPwdRestRepository;
import tn.moatez.project.repository.TokenVerificationRepositroy;
import tn.moatez.project.repository.UserRepository;
import tn.moatez.project.services.AuthService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenPwdRestRepository tokenPwdRestRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    TokenVerificationRepositroy tokenVerificationRepositroy;

    @Override
    public boolean requestChangePwd(String email) {
        if (userRepository.existsByEmail(email)) {
            String token = UUID.randomUUID().toString();

            TokenPwdRest tokenPwdRest = new TokenPwdRest();
            tokenPwdRest.setUser(userRepository.findByEmail(email));
            tokenPwdRest.setToken(token);
            tokenPwdRest.setExpiryDate(LocalDateTime.now().plus(15, ChronoUnit.MINUTES));
            tokenPwdRestRepository.save(tokenPwdRest);
            return true;
        }
        return false;
    }

    @Override
    public boolean changePwd(ChangePwdVerifTokenRequest changePwdVerifTokenRequest) {
        if (userRepository.existsByEmail(changePwdVerifTokenRequest.getEmail())
                && tokenPwdRestRepository.existsByUser_Email(changePwdVerifTokenRequest.getEmail())) {
            TokenPwdRest tokenPwdRest = tokenPwdRestRepository.findTokenPwdRestByUser_Email(changePwdVerifTokenRequest.getEmail());
            if (tokenPwdRest.getToken().equals(changePwdVerifTokenRequest.getToken())
                    && LocalDateTime.now().isBefore(tokenPwdRest.getExpiryDate())) {
                System.out.println(LocalDateTime.now().isBefore(tokenPwdRest.getExpiryDate()));
                User u = userRepository.findByEmail(changePwdVerifTokenRequest.getEmail());
                u.setPassword(encoder.encode(changePwdVerifTokenRequest.getPwd()));
                userRepository.save(u);
                return true;
            }


        }
        return false;
    }

    @Override
    public String requestVerificationUser(String email) {
        if (userRepository.existsByEmail(email)) {
            TokenVerification tokenVerification = new TokenVerification();
            String token = UUID.randomUUID().toString();
            tokenVerification.setUser(userRepository.findByEmail(email));
            tokenVerification.setToken(token);
            tokenVerification.setExpiryDate(LocalDateTime.now().plus(15, ChronoUnit.MINUTES));
            tokenVerificationRepositroy.save(tokenVerification);
            return "http://localhost:8080/auth/verificationemail/" + token + "/" + email;
        }

        return null;
    }

    @Override
    public boolean verificationUser(String token, String email) {
        if (userRepository.existsByEmail(email)
                && tokenVerificationRepositroy.existsByUser_Email(email)) {
            TokenVerification tokenVerification = tokenVerificationRepositroy.findByUser_Email(email);
            if (token.equals(tokenVerification.getToken())
            &&LocalDateTime.now().isBefore(tokenVerification.getExpiryDate())) {
                User usertoverify = userRepository.findByEmail(email);
                usertoverify.setVerified(true);
                userRepository.save(usertoverify);
                tokenVerificationRepositroy.deleteAll();
                return true;
            }
        }
        return false;
    }
}
