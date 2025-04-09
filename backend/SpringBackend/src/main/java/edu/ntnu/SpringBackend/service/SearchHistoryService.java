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

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final Logger logger = LoggerFactory.getLogger(SearchHistoryService.class);

    public List<SearchHistory> findByUser(User user) {
        if (user == null) {
            logger.warn("> User is null, cannot find search history, returning empty list");
            return new ArrayList<SearchHistory>();
        }
        logger.info("> Finding search history by user id: {}", user.getId());
        return searchHistoryRepository.findByUserIdOrderBySearchedAtDesc(user.getId());
    }

    public SearchHistory add(SearchHistoryRequestDTO requestDTO, User user) {
        SearchHistory searchHistory = SearchHistory.builder()
                .user(user)
                .searchQuery(requestDTO.getSearchQuery())
                .searchedAt(LocalDateTime.now())
                .build();

        return this.add(searchHistory, user);
    }

    public SearchHistory add(SearchHistory searchHistory, User user) {
        logger.info("> Adding SearchHistory to user: {}", searchHistory.getUser().getId());
       return searchHistoryRepository.save(searchHistory);
    }
}