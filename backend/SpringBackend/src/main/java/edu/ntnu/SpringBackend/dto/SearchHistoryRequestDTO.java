package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for a search history request.
 * This class is used to transfer a search query from the client to the server.
 * It contains the necessary fields, a searchQuery, to represent a search history request.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryRequestDTO {

  /**
   * The search query.
   */
  private String searchQuery;
}
