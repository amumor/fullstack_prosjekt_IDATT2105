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

import java.util.UUID;


/**
 * UserController handles user-related requests, including getting user information,
 * updating user profiles, and deleting users.
 * It uses the UserService to perform the actual logic for these operations.
 * <p>
 * The controller is secured with Spring Security, allowing only users with the ROLE_ADMIN role
 * to access certain endpoints. It also logs incoming requests and user IDs for debugging purposes.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  /**
   * Gets user information by user ID.
   * <p>
   * This endpoint allows users with the ROLE_ADMIN role to retrieve user information
   * based on the provided user ID.
   *
   * @param user the authenticated user making the request
   * @param id   the UUID of the user to retrieve
   * @return a response entity containing the user information
   */
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

  /**
   * Gets user information by email.
   * <p>
   * This endpoint allows users with the ROLE_ADMIN role to retrieve user information
   * based on the provided email address.
   *
   * @param user  the authenticated user making the request
   * @param email the email address of the user to retrieve
   * @return a response entity containing the user information
   */
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

  /**
   * Updates user information.
   * <p>
   * This endpoint allows users to update their own profile information.
   *
   * @param user           the authenticated user making the request
   * @param userRequestDTO the user request DTO containing updated user information
   * @return a response entity containing the updated user information
   */
  @PutMapping("/update-my-profile")
  @Operation(summary = "Update user info", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<UserResponseDTO> updateUser(
          @AuthenticationPrincipal User user,
          @RequestBody UserRequestDTO userRequestDTO
  ) {
    logger.info("PUT request received on [/api/v1/users/update-my-profile] by: {}", user.getId());
    return ResponseEntity.ok(UserMapper.toDto(userService.updateUser(user, userRequestDTO)));
  }

  /**
   * Gets the authenticated user's profile information.
   * <p>
   * This endpoint allows users to retrieve their own profile information.
   *
   * @param user the authenticated user making the request
   * @return a response entity containing the user's profile information
   */
  @GetMapping("/get-my-profile")
  @Operation(summary = "Getting user info for the logged in user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<UserResponseDTO> getMyProfile(
          @AuthenticationPrincipal User user
  ) {
    logger.info("GET request received on [/api/v1/users/get-my-profile], requested by: {}", user.getId());
    return ResponseEntity.ok(UserMapper.toDto(userService.getUserByEmail(user.getEmail())));
  }

  /**
   * Deletes a user by ID.
   * <p>
   * This endpoint allows users with the ROLE_ADMIN role to delete a user based on the provided user ID.
   *
   * @param user the authenticated user making the request
   * @param id   the UUID of the user to delete
   * @return a response entity indicating the result of the deletion
   */
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
