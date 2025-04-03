package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.SearchHistoryDTO;
import edu.ntnu.SpringBackend.mapper.SearchHistoryMapper;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final Logger logger = LoggerFactory.getLogger(SearchHistoryService.class);
    private final SearchHistoryMapper searchHistoryMapper;
    private final UserService userService;

    public List<SearchHistory> findByUserId(UUID userId) {
        logger.info("Getting search history to user id: {}", userId);
        return searchHistoryRepository.findByUserIdOrderBySearchedAtDesc(userId);
    }

    public SearchHistory add(SearchHistoryDTO requestDTO) {

        // Cant use mapper beacuse it causes a circular dependency when autowiring
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUser(userService.getUserById(requestDTO.getUserId()));
        searchHistory.setSearchQuery(requestDTO.getSearchQuery());
        searchHistory.setSearchedAt(LocalDateTime.parse(requestDTO.getSearchedAt()));
        return add(searchHistory);
    }

    public SearchHistory add(SearchHistory searchHistory) {
        logger.info("Adding SearchHistory with id: {}", searchHistory.getId());
       return searchHistoryRepository.save(searchHistory);
    }
}