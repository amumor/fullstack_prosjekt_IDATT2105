package edu.ntnu.SpringBackend.service;

import java.util.NoSuchElementException;

import edu.ntnu.SpringBackend.dto.AuthenticationRequest;
import edu.ntnu.SpringBackend.dto.AuthenticationResponse;
import edu.ntnu.SpringBackend.dto.RegisterRequest;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public AuthenticationResponse register(RegisterRequest request) { // TODO: protect ADMIN registration with JWT?
        logger.info("Handling register request...");

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            logger.info("> Email already present in database.");
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists.");
        }

        var user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();

        logger.info("> Saving user to the database.");
        repository.save(user);
        logger.info("> Generating jwt token for user [" + request.getEmail() + "].");
        var jwtToken = jwtService.generateToken(user);
        logger.info("> User registered.");
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Handling authentication request...");
        logger.info("> Email: " + request.getEmail());

        try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        } catch (AuthenticationException ae) {
            logger.error("> Authentication failed for email: " + request.getEmail() + ", " + ae.getMessage());
            throw ae;
        }

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("---- User not found with email: " + request.getEmail()));
        logger.info("> User [" + user.getEmail() + "] has been successfully authenticated");
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
