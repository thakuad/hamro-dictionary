package com.hamrodictionary.service;

import com.hamrodictionary.enums.ERole;
import com.hamrodictionary.model.RoleModel;
import com.hamrodictionary.model.UserModel;
import com.hamrodictionary.repository.RoleRepository;
import com.hamrodictionary.repository.UserRepository;
import com.hamrodictionary.security.jwt.JwtUtils;
import com.hamrodictionary.security.payload.request.LoginRequest;
import com.hamrodictionary.security.payload.request.SignupRequest;
import com.hamrodictionary.security.payload.response.JwtResponse;
import com.hamrodictionary.security.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {

        boolean isUserExist = isUserExist(signupRequest.getUsername(), signupRequest.getEmailAddress());

        if (isUserExist) {
            return ResponseEntity.badRequest().body("User Exist in the database");
        } else {
            boolean isPasswordMatch = isPasswordMatch(signupRequest.getPassword(), signupRequest.getConfirmPassword());

            if (isPasswordMatch) {
                String password = encoder.encode(signupRequest.getPassword());
                UserModel user = new UserModel(signupRequest.getUsername(), signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getEmailAddress(), password);

                return saveUserInDb(signupRequest, user);

            } else {

                return ResponseEntity.badRequest().body(new MessageResponse("Password did not match"));
            }
        }
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserService userService = (UserService) authentication.getPrincipal();

        List<String> roles = userService.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        logger.info("The user: " + loginRequest.getUsername() + "successfully sign in into his account at: " + new Date());

        return ResponseEntity.ok(new JwtResponse(jwt, userService.getId(), userService.getUsername(), userService.getEmail(), roles));
    }

    private Boolean isUserExist(String username, String emailAddress) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmailAddress(emailAddress);
    }

    private Boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private ResponseEntity<?> saveUserInDb(SignupRequest signupRequest, UserModel user) {
        Set<RoleModel> roles = new HashSet<>();
        signupRequest.getRoles().forEach(strRole -> {
            RoleModel roleModel = roleRepository.findByName(strRole);
            if (roleModel != null) {
                roles.add(roleModel);
                user.setRoles(roles);
            } else {
                RoleModel roleM = new RoleModel();
                roleM.setName(ERole.ROLE_USER);
                roles.add(roleM);
                user.setRoles(roles);
                roleRepository.save(roleM);
            }
        });
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User register successfully!"));
    }

}
