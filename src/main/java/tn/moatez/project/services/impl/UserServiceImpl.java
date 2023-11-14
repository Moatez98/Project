package tn.moatez.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.moatez.project.dto.UserDTO;
import tn.moatez.project.enums.ERole;
import tn.moatez.project.model.TokenPwdRest;
import tn.moatez.project.model.User;
import tn.moatez.project.payload.request.ChangePwdVerifTokenRequest;
import tn.moatez.project.payload.request.EmailDetail;
import tn.moatez.project.repository.RoleRepository;
import tn.moatez.project.repository.TokenPwdRestRepository;
import tn.moatez.project.repository.UserRepository;
import tn.moatez.project.services.EmailService;
import tn.moatez.project.services.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    TokenPwdRestRepository tokenPwdRestRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDTO addUser(UserDTO userDTO)  {
        User user = User.mapToEntity(userDTO);
        user.setRoles(roleRepository.findRolesByName(ERole.ROLE_USER));
        UserDTO useraftersave = UserDTO.mapToDTO(userRepository.save(user));
        emailService.sendSimpleMail(new EmailDetail(userDTO.getEmail(),"welcome To Petsy Gabes"));
        return useraftersave;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTO.mapToDTO(userRepository.findByEmail(email));
    }

    @Override
    public boolean requestChangePwd(String email) {
        if(userRepository.existsByEmail(email)) {
            String token = UUID.randomUUID().toString();

            TokenPwdRest tokenPwdRest = new TokenPwdRest();
            tokenPwdRest.setUser(userRepository.findByEmail(email));
            tokenPwdRest.setToken(token);
            tokenPwdRest.setExpiryDate(LocalDateTime.now().plus(15, ChronoUnit.MINUTES));
            if(tokenPwdRestRepository.save(tokenPwdRest)!=null){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean changePwd(ChangePwdVerifTokenRequest changePwdVerifTokenRequest) {
        if(userRepository.existsByEmail(changePwdVerifTokenRequest.getEmail())
                && tokenPwdRestRepository.existsByUser_Email(changePwdVerifTokenRequest.getEmail())){
            TokenPwdRest tokenPwdRest = tokenPwdRestRepository.findTokenPwdRestByUser_Email(changePwdVerifTokenRequest.getEmail());
            if(tokenPwdRest.getToken().equals(changePwdVerifTokenRequest.getToken())){
                User u = userRepository.findByEmail(changePwdVerifTokenRequest.getEmail());
                u.setPassword(encoder.encode(changePwdVerifTokenRequest.getPwd()));
                userRepository.save(u);
                return true;
            }


        }
        return false;
    }

}
