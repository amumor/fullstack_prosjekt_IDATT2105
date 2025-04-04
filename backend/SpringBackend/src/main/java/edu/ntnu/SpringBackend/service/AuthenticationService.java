package edu.ntnu.SpringBackend.service;

import java.util.NoSuchElementException;

import edu.ntnu.SpringBackend.dto.AuthenticationRequestDTO;
import edu.ntnu.SpringBackend.dto.TokenResponseDTO;
import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private final UserService userService;

    public TokenResponseDTO register(UserRequestDTO request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            logger.info("> Email already present in database.");
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists.");
        }

        logger.info("> Validating user data.");
        userService.validateName(request.getFirstName());
        userService.validateName(request.getLastName());
        userService.validatePhone(request.getPhoneNumber());
        userService.validatePassword(request.getPassword());
        userService.validateEmail(request.getEmail());
        logger.info("> User data validated.");

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
        var jwtToken = jwtService.generateToken(user);
        logger.info("> User registered.");
        return TokenResponseDTO.builder().token(jwtToken).build();
    }

    public TokenResponseDTO authenticate(AuthenticationRequestDTO request) {
        logger.info("> Authentication user with email: " + request.getEmail());

        try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        } catch (AuthenticationException ae) {
            logger.error("!!! Authentication failed for email: " + request.getEmail() + ", " + ae.getMessage());
            throw ae;
        }

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + request.getEmail()));
        logger.info("> User [{}] has been successfully authenticated", user.getId());
        var jwtToken = jwtService.generateToken(user);
        return TokenResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}
