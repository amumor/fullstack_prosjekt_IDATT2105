package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.UserRequestDTO;
import edu.ntnu.SpringBackend.dto.UserResponseDTO;
import edu.ntnu.SpringBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  // not necessary ?? AuthenticationController handles this
  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO user) {
    return ResponseEntity.ok(userService.addUser(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UserRequestDTO userRequestDTO) {
    return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }


}
