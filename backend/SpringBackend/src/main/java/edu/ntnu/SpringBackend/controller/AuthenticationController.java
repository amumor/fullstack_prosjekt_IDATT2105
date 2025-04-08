package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.AuthenticationRequestDTO;
import edu.ntnu.SpringBackend.dto.TokenResponseDTO;
import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        if (request.getRole().equals(Role.ROLE_ADMIN)) {
            logger.error("!!! User is trying to register as ROLE_ADMIN without authorization.");
            throw new IllegalArgumentException("Unauthorized request to register as ADMIN.");
        }

        return ResponseEntity.ok(service.register(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register/admin")
    @Operation(summary = ("Register a new admin user"), security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<TokenResponseDTO> registerAdmin(
            @AuthenticationPrincipal User user,
            @RequestBody UserRequestDTO request
    ) {
        logger.info("POST request received on [/api/v1/auth/register/admin]");
        logger.info("User role: {}", user.getRole().toString());
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        logger.info("POST request received on [/api/v1/auth/authenticate]");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
