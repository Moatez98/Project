package tn.moatez.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.moatez.project.dto.UserDTO;
import tn.moatez.project.enums.ERole;
import tn.moatez.project.model.User;
import tn.moatez.project.repository.RoleRepository;
import tn.moatez.project.repository.UserRepository;
import tn.moatez.project.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = User.mapToEntity(userDTO);
        user.setRoles(roleRepository.findRolesByName(ERole.ROLE_USER));
        UserDTO useraftersave = UserDTO.mapToDTO(userRepository.save(user));

        return useraftersave;
    }
}
