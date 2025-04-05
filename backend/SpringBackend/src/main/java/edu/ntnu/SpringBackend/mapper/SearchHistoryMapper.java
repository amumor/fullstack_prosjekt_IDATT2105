package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.SearchHistoryResponseDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryListResponseDTO;
import edu.ntnu.SpringBackend.model.SearchHistory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchHistoryMapper {
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