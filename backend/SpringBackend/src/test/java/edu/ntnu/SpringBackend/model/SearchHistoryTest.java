package edu.ntnu.SpringBackend.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class SearchHistoryTest {

  @Test
  public void testBuilderAndGetters() {
    UUID id = UUID.randomUUID();
    UUID dummyUserId = UUID.randomUUID();
    User dummyUser = new User();
    dummyUser.setId(dummyUserId);

    String query = "dummy search query";
    LocalDateTime now = LocalDateTime.now();

    SearchHistory sh = SearchHistory.builder()
            .id(id)
            .user(dummyUser)
            .searchQuery(query)
            .searchedAt(now)
            .build();

    assertEquals(id, sh.getId());
    assertEquals(dummyUser, sh.getUser());
    assertEquals(query, sh.getSearchQuery());
    assertEquals(now, sh.getSearchedAt());
  }

  @Test
  public void testSetters() {
    SearchHistory sh = new SearchHistory();
    UUID id = UUID.randomUUID();
    sh.setId(id);
    String query = "setter query";
    sh.setSearchQuery(query);
    LocalDateTime now = LocalDateTime.now();
    sh.setSearchedAt(now);

    User dummyUser = new User();
    dummyUser.setId(UUID.randomUUID());
    sh.setUser(dummyUser);

    assertEquals(id, sh.getId());
    assertEquals(query, sh.getSearchQuery());
    assertEquals(now, sh.getSearchedAt());
    assertEquals(dummyUser, sh.getUser());
  }

  @Test
  public void testToStringContainsExpectedValues() {
    User dummyUser = new User();
    dummyUser.setId(UUID.randomUUID());
    SearchHistory sh = SearchHistory.builder()
            .searchQuery("toString query")
            .searchedAt(LocalDateTime.now())
            .user(dummyUser)
            .build();
    String str = sh.toString();
    assertNotNull(str);
    assertTrue(str.contains("toString query"));
  }
}
