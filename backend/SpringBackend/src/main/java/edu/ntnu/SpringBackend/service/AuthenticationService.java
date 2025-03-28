package edu.ntnu.SpringBackend.service;

import java.util.NoSuchElementException;

import edu.ntnu.SpringBackend.auth.AuthenticationResponse;
import edu.ntnu.SpringBackend.auth.RegisterRequest;
import edu.ntnu.SpringBackend.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository; // TODO: fix when
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(
        AuthenticationService.class
    );

    public AuthenticationResponse register(RegisterRequest request) {
        logger.info("Handling register request...");
        var user = User.builder() // TODO: dobble check details in login form
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUserName())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
