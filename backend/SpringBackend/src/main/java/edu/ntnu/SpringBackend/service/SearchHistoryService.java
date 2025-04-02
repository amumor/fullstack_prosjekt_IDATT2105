package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.SearchHistoryDTO;
import edu.ntnu.SpringBackend.mapper.SearchHistoryMapper;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final Logger logger = LoggerFactory.getLogger(SearchHistoryService.class);
    private final SearchHistoryMapper searchHistoryMapper;

    public List<SearchHistory> findByUserId(UUID userId) {
        logger.info("Getting search history to user id: {}", userId);
        return searchHistoryRepository.findByUserIdOrderBySearchedAtDesc(userId);
    }

    public SearchHistory add(SearchHistoryDTO requestDTO) {
        SearchHistory searchHistory = searchHistoryMapper.toEntity(requestDTO);
        return add(searchHistory);
    }

    public SearchHistory add(SearchHistory searchHistory) {
        logger.info("Adding SearchHistory with id: {}", searchHistory.getId());
       return searchHistoryRepository.save(searchHistory);
    }
}