package tn.moatez.project.services;

import tn.moatez.project.dto.UserDTO;
import tn.moatez.project.model.User;
import tn.moatez.project.payload.request.ChangePwdVerifTokenRequest;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    UserDTO getUserByEmail(String email);
    boolean requestChangePwd(String email);
    boolean changePwd(ChangePwdVerifTokenRequest changePwdVerifTokenRequest);
}
