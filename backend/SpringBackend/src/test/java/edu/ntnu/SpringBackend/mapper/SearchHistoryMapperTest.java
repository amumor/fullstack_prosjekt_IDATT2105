package edu.ntnu.SpringBackend.mapper;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.SpringBackend.dto.SearchHistoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryResponseDTO;
import edu.ntnu.SpringBackend.model.SearchHistory;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SearchHistoryMapperTest {

  private final SearchHistoryMapper mapper = new SearchHistoryMapper();

  @Test
  public void testListToDto_NullInput_ThrowsException() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            mapper.toDto((List<SearchHistory>) null)
    );
    assertEquals("searchHistoryList argument can not be null", ex.getMessage());
  }

  @Test
  public void testListToDto_ValidInput() {
    SearchHistory sh1 = SearchHistory.builder()
            .searchQuery("query1")
            .searchedAt(LocalDateTime.now())
            .build();
    SearchHistory sh2 = SearchHistory.builder()
            .searchQuery("query2")
            .searchedAt(LocalDateTime.now())
            .build();

    SearchHistoryListResponseDTO dto = mapper.toDto(Arrays.asList(sh1, sh2));
    assertNotNull(dto);
    assertNotNull(dto.getSearchQueries());
    assertEquals(2, dto.getSearchQueries().size());
    assertTrue(dto.getSearchQueries().containsAll(Arrays.asList("query1", "query2")));
  }

  @Test
  public void testToDto_Single_NullInput_ThrowsException() {
    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
            mapper.toDto((SearchHistory)null)
    );
    assertEquals("searchHistory argument can not be null", ex.getMessage());
  }

  @Test
  public void testToDto_Single_ValidInput() {
    LocalDateTime now = LocalDateTime.now();
    SearchHistory sh = SearchHistory.builder()
            .searchQuery("test query")
            .searchedAt(now)
            .build();
    SearchHistoryResponseDTO dto = mapper.toDto(sh);
    assertNotNull(dto);
    assertEquals("test query", dto.getSearchQuery());
    assertEquals(now, dto.getSearchedAt());
  }
}
