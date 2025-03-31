package edu.ntnu.SpringBackend.service;

import java.util.NoSuchElementException;

import edu.ntnu.SpringBackend.dto.AuthenticationRequest;
import edu.ntnu.SpringBackend.dto.AuthenticationResponse;
import edu.ntnu.SpringBackend.dto.RegisterRequest;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.UserRepository;
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

    private final UserRepository repository;
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
                .phoneNumber(request.getPhoneNumber())
                .build();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Handling authentication request...");
        logger.info("---- email: " + request.getEmail());
        logger.info("---- password: " + request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        logger.info("request email: " + request.getEmail());
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + request.getEmail()));


        logger.info("User [" + user.getEmail() + "] has been successfully authenticated");
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
