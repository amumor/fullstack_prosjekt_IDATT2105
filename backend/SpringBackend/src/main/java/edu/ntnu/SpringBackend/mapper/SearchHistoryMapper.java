package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.SearchHistoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryResponseDTO;
import edu.ntnu.SpringBackend.model.SearchHistory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting SearchHistory entities to DTOs and vice versa.
 * This class is responsible for transforming the data between the entity and DTO layers.
 * <p>
 * It contains methods to convert a single SearchHistory entity to a SearchHistoryResponseDTO,
 * as well as a method to convert a list of SearchHistory entities to a SearchHistoryListResponseDTO.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Component
public class SearchHistoryMapper {

  /**
   * Converts a list of SearchHistory entities to a SearchHistoryListResponseDTO.
   *
   * @param searchHistoryList the list of SearchHistory entities to convert
   * @return the converted SearchHistoryListResponseDTO
   * @throws IllegalArgumentException if the searchHistoryList argument is null
   */
  public SearchHistoryListResponseDTO toDto(List<SearchHistory> searchHistoryList) {
    if (searchHistoryList == null) {
      throw new IllegalArgumentException("searchHistoryList argument can not be null");
    }

    List<String> searchQueryList = new ArrayList<>();
    for (SearchHistory searchHistory : searchHistoryList) {
      searchQueryList.add(searchHistory.getSearchQuery());
    }

    SearchHistoryListResponseDTO dto = new SearchHistoryListResponseDTO();
    dto.setSearchQueries(searchQueryList);

    return dto;
  }

  /**
   * Converts a SearchHistory entity to a SearchHistoryResponseDTO.
   *
   * @param searchHistory The SearchHistory entity to convert.
   * @return The converted SearchHistoryResponseDTO.
   * @throws IllegalArgumentException if the searchHistory argument is null.
   */
  public SearchHistoryResponseDTO toDto(SearchHistory searchHistory) {
    if (searchHistory == null) {
      throw new IllegalArgumentException("searchHistory argument can not be null");
    }

    SearchHistoryResponseDTO dto = new SearchHistoryResponseDTO();
    dto.setSearchQuery(searchHistory.getSearchQuery());
    dto.setSearchedAt(searchHistory.getSearchedAt());

    return dto;
  }
}