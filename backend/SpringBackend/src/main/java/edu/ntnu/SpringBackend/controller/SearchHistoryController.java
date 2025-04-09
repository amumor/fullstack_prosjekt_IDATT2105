package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.SearchHistoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryRequestDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryResponseDTO;
import edu.ntnu.SpringBackend.mapper.SearchHistoryMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.SearchHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing search history.
 * This class provides endpoints for adding and retrieving search history entries.
 * It uses the SearchHistoryService to perform the actual business logic and the SearchHistoryMapper
 * to convert between entity and DTO objects.
 * <p>
 * The controller is secured with Spring Security,
 * allowing only authenticated users to access the endpoints.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {
  private final Logger logger = LoggerFactory.getLogger(SearchHistoryController.class);
  private final SearchHistoryMapper searchHistoryMapper;
  private final SearchHistoryService searchHistoryService;

  /**
   * Get search history for the authenticated user.
   * Only authenticated users can get their search history.
   *
   * @param user the authenticated user
   * @return a list of search history response DTOs
   */
  @GetMapping("/get-my-history")
  @Operation(summary = "Get search history for the authenticated user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<SearchHistoryListResponseDTO> findByUserId(
          @AuthenticationPrincipal User user
  ) {
    logger.info("GET Request received on [/api/v1/search-history/get-my-history]");
    return ResponseEntity.ok(searchHistoryMapper.toDto(searchHistoryService.findByUser(user)));
  }

  /**
   * Add a new search history entry for the authenticated user.
   * Only authenticated users can add search history entries.
   *
   * @param user    the authenticated user
   * @param request the search history request DTO containing search details
   * @return a response entity containing the created search history entry
   */
  @PostMapping("/add")
  @Operation(summary = "Add a new search history entry for the authenticated user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<SearchHistoryResponseDTO> add(
          @AuthenticationPrincipal User user,
          @RequestBody SearchHistoryRequestDTO request
  ) {
    logger.info("POST Request received on [/api/v1/search-history/add]");
    return ResponseEntity.ok(searchHistoryMapper.toDto(searchHistoryService.add(request, user)));
  }
}
