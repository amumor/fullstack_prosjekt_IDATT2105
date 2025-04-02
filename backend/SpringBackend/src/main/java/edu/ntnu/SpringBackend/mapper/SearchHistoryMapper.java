package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.SearchHistoryDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryListResponseDTO;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.service.SearchHistoryService;
import edu.ntnu.SpringBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchHistoryMapper {
    @Autowired
    private UserService userService;

    @Autowired
    private SearchHistoryService searchHistoryService;

    public SearchHistory toEntity(SearchHistoryDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto argument can not be null");
        }

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUser(userService.getUserById(dto.getUserId()));
        searchHistory.setSearchQuery(dto.getSearchQuery());
        searchHistory.setSearchedAt(LocalDateTime.parse(dto.getSearchedAt()));

        return searchHistory;
    }


    public SearchHistoryListResponseDTO toDto(List<SearchHistory> searchHistoryList) {
        if (searchHistoryList == null) {
            throw new IllegalArgumentException("searchHistoryList argument can not be null");
        }

        List<String> searchQueryList = new ArrayList<>();
        for (SearchHistory searchHistory : searchHistoryList) {
            searchQueryList.add(searchHistory.getSearchQuery());
        }

        SearchHistoryListResponseDTO dto = new SearchHistoryListResponseDTO();
        dto.setUserId(searchHistoryList.getFirst().getId());
        dto.setSearchQueries(searchQueryList);

        return dto;
    }

    public SearchHistoryDTO toDto(SearchHistory searchHistory) {
        if (searchHistory == null) {
            throw new IllegalArgumentException("searchHistory argument can not be null");
        }

        SearchHistoryDTO dto = new SearchHistoryDTO();
        dto.setUserId(searchHistory.getUser().getId());
        dto.setSearchQuery(searchHistory.getSearchQuery());
        dto.setSearchedAt(searchHistory.getSearchedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return dto;
    }
}