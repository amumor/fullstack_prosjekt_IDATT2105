package edu.ntnu.SpringBackend.service;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import no.ntnu.idi.calculatorBackend.config.JwtService;
import no.ntnu.idi.calculatorBackend.model.user.Role;
import no.ntnu.idi.calculatorBackend.model.user.User;
import no.ntnu.idi.calculatorBackend.model.user.UserRepository;
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
        var user = User.builder()
            .firstname(request.getFirstName())
            .lastname(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        repository.save(user);
        logger.info(
            "User [" + user.getEmail() + "] has been registered successfully"
        );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
