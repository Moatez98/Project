package tn.moatez.project.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.moatez.project.dto.UserDTO;
import tn.moatez.project.payload.request.ChangePwdRequest;
import tn.moatez.project.payload.request.ChangePwdVerifTokenRequest;
import tn.moatez.project.payload.request.LoginRequest;
import tn.moatez.project.payload.response.UserInfoResponse;
import tn.moatez.project.repository.RoleRepository;
import tn.moatez.project.repository.UserRepository;
import tn.moatez.project.security.jwt.JwtProvider;
import tn.moatez.project.security.jwt.service.UserPrinciple;
import tn.moatez.project.services.AuthService;
import tn.moatez.project.services.UserService;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthenticationManager authenticationManager;


    UserRepository userRepository;


    RoleRepository roleRepository;


    PasswordEncoder encoder;


    JwtProvider jwtUtils;

    UserService userService;

    AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder,
                          JwtProvider jwtUtils, UserService userService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.authService = authService;
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid@RequestBody UserDTO userDTO){
        if (Boolean.TRUE
                .equals(userRepository.existsByUsername(userDTO.getUsername()))) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber()))) {
            return ResponseEntity.badRequest().body("Error: Phone number is already taken!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userDTO.getEmail()))) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userDTO.setVerified(false);
        UserDTO userSaved = userService.addUser(userDTO);
        if (userSaved!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(userSaved);

        }
return null;


    }
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logout");
    }
    @PostMapping("/requestchangepwd")
    public ResponseEntity<Boolean> test(@RequestBody ChangePwdRequest changePwdRequest){
        if(authService.requestChangePwd(changePwdRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(false);
    }
    @PostMapping("/forgetpwd")
    public ResponseEntity<Boolean> changepwd(@RequestBody ChangePwdVerifTokenRequest changePwdVerifTokenRequest){
        if(authService.changePwd(changePwdVerifTokenRequest)){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(false);
    }
    @PostMapping("/requestverifcation")
    public ResponseEntity<?> requestverifcation(@RequestBody ChangePwdRequest changePwdRequest){

        return ResponseEntity.status(HttpStatus.OK).body(authService.requestVerificationUser(changePwdRequest.getEmail()));
    }
    @GetMapping("/verificationemail/{token}/{email}")
    public  ResponseEntity<?> verficationemail(@PathVariable("token")String token,
                                               @PathVariable("email")String email){
        return ResponseEntity.status(HttpStatus.OK).body(authService.verificationUser(token,email));

    }

}
