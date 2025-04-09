package edu.ntnu.SpringBackend.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.SpringBackend.model.enums.BidStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BidTest {

  @Test
  public void testOnCreateSetsTimestampAndDefaultStatus() {
    Bid bid = new Bid();
    bid.setStatus(null); // simulate no status set prior to persisting
    bid.onCreate();
    assertNotNull(bid.getTimestamp(), "Timestamp should be initialized by onCreate");
    assertEquals(BidStatus.PENDING, bid.getStatus(), "Status should default to PENDING");
  }

  @Test
  public void testBuilderCreatesBid() {
    Chat chat = Mockito.mock(Chat.class);
    User buyer = Mockito.mock(User.class);
    UUID id = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();

    Bid bid = Bid.builder()
            .id(id)
            .chat(chat)
            .buyer(buyer)
            .price(100.0)
            .status(BidStatus.ACCEPTED)
            .timestamp(now)
            .build();

    assertEquals(id, bid.getId());
    assertEquals(chat, bid.getChat());
    assertEquals(buyer, bid.getBuyer());
    assertEquals(100.0, bid.getPrice());
    assertEquals(BidStatus.ACCEPTED, bid.getStatus());
    assertEquals(now, bid.getTimestamp());
  }
}
