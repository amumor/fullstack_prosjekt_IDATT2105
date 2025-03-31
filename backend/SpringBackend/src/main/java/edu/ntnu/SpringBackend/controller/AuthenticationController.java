package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.AuthenticationRequest;
import edu.ntnu.SpringBackend.dto.AuthenticationResponse;
import edu.ntnu.SpringBackend.dto.RegisterRequest;
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
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        logger.info("Request recieved on [/api/v1/auth/register]");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        logger.info("Request recieved on [/api/v1/auth/authenticate]");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
