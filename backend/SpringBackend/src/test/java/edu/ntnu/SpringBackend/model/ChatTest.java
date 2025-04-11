package edu.ntnu.SpringBackend.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ChatTest {

  @Test
  public void testOnCreateSetsCreatedAt() {
    Chat chat = new Chat();
    chat.onCreate();
    assertNotNull(chat.getCreatedAt(), "createdAt should be set by onCreate");
  }

  @Test
  public void testBuilderAndToString() {
    User buyer = Mockito.mock(User.class);
    Listing listing = Mockito.mock(Listing.class);
    UUID listingId = UUID.randomUUID();
    when(listing.getId()).thenReturn(listingId);

    UUID id = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();
    Chat chat = Chat.builder()
            .id(id)
            .buyer(buyer)
            .listing(listing)
            .createdAt(now)
            .messages(Collections.emptyList())
            .bids(Collections.emptyList())
            .build();

    assertEquals(id, chat.getId());
    assertEquals(buyer, chat.getBuyer());
    assertEquals(listing, chat.getListing());
    assertEquals(now, chat.getCreatedAt());
    assertTrue(chat.toString().contains("Chat{"));
  }
}
