package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.SearchHistoryRequestDTO;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing search history.
 * This class provides methods to add and retrieve search history for users.
 * The service saves search history entries to the database and retrieves them based on user ID.
 * This means the user can get results based on their search history.
 * It interacts with the SearchHistoryRepository to perform CRUD operations.
 *
 * @author Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SearchHistoryService {

  private final SearchHistoryRepository searchHistoryRepository;
  private final Logger logger = LoggerFactory.getLogger(SearchHistoryService.class);

  /**
   * Fetches all search history for a user.
   *
   * @param user The user whose search history is to be fetched.
   * @return List of search history for the current user.
   */
  public List<SearchHistory> findByUser(User user) {
    if (user == null) {
      logger.warn("> User is null, cannot find search history, returning empty list");
      return new ArrayList<SearchHistory>();
    }
    logger.info("> Finding search history by user id: {}", user.getId());
    return searchHistoryRepository.findByUserIdOrderBySearchedAtDesc(user.getId());
  }

  /**
   * Adds a new search history entry for a user.
   *
   * @param requestDTO The request DTO containing the search query.
   * @param user       The user who is adding the search history.
   * @return The created search history entry.
   */
  public SearchHistory add(SearchHistoryRequestDTO requestDTO, User user) {
    SearchHistory searchHistory = SearchHistory.builder()
            .user(user)
            .searchQuery(requestDTO.getSearchQuery())
            .searchedAt(LocalDateTime.now())
            .build();

    return this.add(searchHistory, user);
  }

  /**
   * Adds a new search history entry for a user.
   *
   * @param searchHistory The search history entry to be added.
   * @param user          The user who is adding the search history.
   * @return The created search history entry.
   */
  public SearchHistory add(SearchHistory searchHistory, User user) {
    logger.info("> Adding SearchHistory to user: {}", searchHistory.getUser().getId());
    return searchHistoryRepository.save(searchHistory);
  }
}