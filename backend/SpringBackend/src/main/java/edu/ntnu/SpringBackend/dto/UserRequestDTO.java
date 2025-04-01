package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
