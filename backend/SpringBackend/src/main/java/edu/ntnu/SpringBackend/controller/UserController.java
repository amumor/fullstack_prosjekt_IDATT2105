package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.mapper.UserMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{id}")
    @Operation(summary = "Getting user info by user id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDTO> getUserById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("GET request received on [/api/v1/users/id/{}], requested by: {}", id, user.getId());
        return ResponseEntity.ok(UserMapper.toDto(userService.getUserById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    @Operation(summary = "Getting user info by email", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @AuthenticationPrincipal User user,
            @PathVariable String email
    ) {
        logger.info("GET request received on [/api/v1/users/email/{}], requested by: {}", email, user.getId());
        return ResponseEntity.ok(UserMapper.toDto(userService.getUserByEmail(email)));
    }

    @PutMapping("/update-my-profile")
    @Operation(summary = "Update user info", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDTO> updateUser(
            @AuthenticationPrincipal User user,
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        logger.info("PUT request received on [/api/v1/users/update-my-profile] by: {}", user.getId());
        return ResponseEntity.ok(UserMapper.toDto(userService.updateUser(user, userRequestDTO)));
    }

    @GetMapping("/get-my-profile")
    @Operation(summary = "Getting user info by email", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDTO> getMyProfile(
            @AuthenticationPrincipal User user
    ) {
        logger.info("GET request received on [/api/v1/users/get-my-profile], requested by: {}", user.getId());
        return ResponseEntity.ok(UserMapper.toDto(userService.getUserByEmail(user.getEmail())));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("DELETE request received on [/api/v1/users/{}]", id);
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
