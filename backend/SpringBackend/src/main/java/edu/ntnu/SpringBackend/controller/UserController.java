package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.mapper.UserMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.UserService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("GET request received on [/api/v1/users/id/{}], requested by: {}", id, user.getId());
        return ResponseEntity.ok(UserMapper.toDto(userService.getUserById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @AuthenticationPrincipal User user,
            @PathVariable String email
    ) {
        logger.info("GET request received on [/api/v1/users/email/{}], requested by: {}", email, user.getId());
        return ResponseEntity.ok(UserMapper.toDto(userService.getUserByEmail(email)));
    }

  @PutMapping("/update-my-profile")
  public ResponseEntity<UserResponseDTO> updateUser(
          @AuthenticationPrincipal User user,
          @RequestBody UserRequestDTO userRequestDTO
  ) {
    logger.info("PUT request received on [/api/v1/users/update-my-profile] by: {}", user.getId());
    return ResponseEntity.ok(UserMapper.toDto(userService.updateUser(user, userRequestDTO)));
  }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("DELETE request received on [/api/v1/users/{}]", id);
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
