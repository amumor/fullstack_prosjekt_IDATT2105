package edu.ntnu.SpringBackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.dto.SearchHistoryRequestDTO;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.SearchHistoryRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class SearchHistoryServiceTest {

  @Mock
  private SearchHistoryRepository searchHistoryRepository;

  @InjectMocks
  private SearchHistoryService searchHistoryService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFindByUser_NullUser_ReturnsEmptyList() {
    List<SearchHistory> histories = searchHistoryService.findByUser(null);
    assertNotNull(histories);
    assertTrue(histories.isEmpty());
  }

  @Test
  public void testFindByUser_ValidUser_ReturnsHistories() {
    User user = mock(User.class);
    UUID userId = UUID.randomUUID();
    when(user.getId()).thenReturn(userId);

    SearchHistory sh1 = SearchHistory.builder().id(UUID.randomUUID()).searchQuery("query1").searchedAt(LocalDateTime.now()).build();
    SearchHistory sh2 = SearchHistory.builder().id(UUID.randomUUID()).searchQuery("query2").searchedAt(LocalDateTime.now()).build();
    List<SearchHistory> expectedList = Arrays.asList(sh1, sh2);

    when(searchHistoryRepository.findByUserIdOrderBySearchedAtDesc(userId)).thenReturn(expectedList);

    List<SearchHistory> result = searchHistoryService.findByUser(user);
    assertEquals(expectedList, result);
    verify(searchHistoryRepository, times(1)).findByUserIdOrderBySearchedAtDesc(userId);
  }

  @Test
  public void testAdd_WithRequestDTO() {
    User user = mock(User.class);
    when(user.getId()).thenReturn(UUID.randomUUID());

    SearchHistoryRequestDTO requestDTO = mock(SearchHistoryRequestDTO.class);
    when(requestDTO.getSearchQuery()).thenReturn("dummy query");

    SearchHistory savedHistory = SearchHistory.builder()
            .id(UUID.randomUUID())
            .user(user)
            .searchQuery("dummy query")
            .searchedAt(LocalDateTime.now())
            .build();
    when(searchHistoryRepository.save(any(SearchHistory.class))).thenReturn(savedHistory);

    SearchHistory result = searchHistoryService.add(requestDTO, user);
    assertNotNull(result);
    assertEquals("dummy query", result.getSearchQuery());
    assertEquals(user, result.getUser());
  }

  @Test
  public void testAdd_WithSearchHistory() {
    User user = mock(User.class);
    when(user.getId()).thenReturn(UUID.randomUUID());
    SearchHistory searchHistory = SearchHistory.builder()
            .id(UUID.randomUUID())
            .user(user)
            .searchQuery("test query")
            .searchedAt(LocalDateTime.now())
            .build();

    when(searchHistoryRepository.save(searchHistory)).thenReturn(searchHistory);

    SearchHistory result = searchHistoryService.add(searchHistory, user);
    assertEquals(searchHistory, result);
    verify(searchHistoryRepository, times(1)).save(searchHistory);
  }
}
