package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for a list of categories.
 * This class is used to transfer a list of categories from the server to the client.
 * It contains a list of CategoryResponseDTO objects.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 * @see CategoryResponseDTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponseDTO {

    /**
     * A list of CategoryResponseDTOs.
     */
    List<CategoryResponseDTO> categories;
}
