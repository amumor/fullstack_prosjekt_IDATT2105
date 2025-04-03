package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.mapper.UserMapper;
import edu.ntnu.SpringBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  @GetMapping("/id/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(
          @PathVariable UUID id
  ) {
    logger.info("GET request received on [/api/v1/users/id/{}]", id);
    return ResponseEntity.ok(UserMapper.toDto(userService.getUserById(id)));
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<UserResponseDTO> getUserByEmail(
          @PathVariable String email
  ) {
    logger.info("GET request received on [/api/v1/users/email/{}]", email);
    return ResponseEntity.ok(UserMapper.toDto(userService.getUserByEmail(email)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(
          @PathVariable UUID id,
          @RequestBody UserRequestDTO userRequestDTO
  ) {
    logger.info("PUT request received on [/api/v1/users/{}]", id);
    return ResponseEntity.ok(UserMapper.toDto(userService.updateUser(id, userRequestDTO)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(
          @PathVariable UUID id
  ) {
    logger.info("DELETE request received on [/api/v1/users/{}]", id);
    userService.deleteUserById(id);
    return ResponseEntity.ok().build();
  }


}
