package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.AuthenticationRequestDTO;
import edu.ntnu.SpringBackend.dto.TokenResponseDTO;
import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(
            @RequestBody UserRequestDTO request
    ) {
        logger.info("POST request recieved on [/api/v1/auth/register]");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        logger.info("POST request recieved on [/api/v1/auth/authenticate]");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
