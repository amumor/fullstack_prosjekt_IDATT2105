package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for a search history response.
 * This class is used to transfer a search query and the time it was searched from the server to the client.
 * It contains the necessary fields, a searchQuery and searchedAt, to represent a search history response.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryResponseDTO {

  /**
   * The search query.
   */
  private String searchQuery;

  /**
   * The time the search query was searched.
   */
  private LocalDateTime searchedAt;
}
