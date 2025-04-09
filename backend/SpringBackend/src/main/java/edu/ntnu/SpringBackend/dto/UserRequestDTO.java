package edu.ntnu.SpringBackend.dto;

import edu.ntnu.SpringBackend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRequestDTO is a data transfer object (DTO) used for user registration requests.
 * It contains the user's first name, last name, email, password, phone number, and role.
 * <p>
 * This class is used to encapsulate the user registration request data
 * and is typically used in the registration process.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The phone number of the user.
     */
    private String phoneNumber;

    /**
     * The role of the user.
     */
    private Role role;
}
