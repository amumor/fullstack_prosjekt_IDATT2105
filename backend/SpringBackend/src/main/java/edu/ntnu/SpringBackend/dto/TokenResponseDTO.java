package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * TokenResponseDTO is a data transfer object (DTO) used for token response data.
 * It contains the token string.
 * <p>
 * This class is used to encapsulate the token response data
 * and is typically used in the response of authentication-related operations.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {

    /**
     * The token string.
     */
    private String token;
}
