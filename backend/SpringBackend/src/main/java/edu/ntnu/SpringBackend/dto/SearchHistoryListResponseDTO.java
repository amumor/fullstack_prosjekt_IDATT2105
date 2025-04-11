package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for a list of search history items.
 * This class is used to transfer a list of search queries from the server to the client.
 * It contains a list of search queries.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryListResponseDTO {

  /**
   * A list of search queries.
   */
  private List<String> SearchQueries;
}
