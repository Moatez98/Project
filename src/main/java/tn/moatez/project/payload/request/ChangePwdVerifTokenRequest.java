package tn.moatez.project.payload.request;

import lombok.Data;

@Data
public class ChangePwdVerifTokenRequest {
    String email;
    String token;
    String pwd;
}
