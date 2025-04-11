package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for a list of listings.
 * This class is used to transfer a list of listings from the server to the client.
 * It contains a list of ListingResponseDTO objects.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingListResponseDTO {

  /**
   * A list of ListingResponseDTOs.
   */
  private List<ListingResponseDTO> listings;
}
