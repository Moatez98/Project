package tn.moatez.project.services;

import tn.moatez.project.payload.request.ChangePwdVerifTokenRequest;

public interface AuthService {
    boolean requestChangePwd(String email);
    boolean changePwd(ChangePwdVerifTokenRequest changePwdVerifTokenRequest);
    String requestVerificationUser(String email);
    boolean verificationUser(String token,String email);
}
