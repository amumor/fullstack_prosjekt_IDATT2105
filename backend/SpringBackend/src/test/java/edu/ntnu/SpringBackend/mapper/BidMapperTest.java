package edu.ntnu.SpringBackend.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.model.Bid;
import edu.ntnu.SpringBackend.model.Chat;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import edu.ntnu.SpringBackend.model.enums.BidStatus;

public class BidMapperTest {

  @Test
  public void testToDto() {
    BidMapper mapper = new BidMapper();
    Chat chat = mock(Chat.class);
    UUID chatId = UUID.randomUUID();
    when(chat.getId()).thenReturn(chatId);

    Bid bid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .price(200.0)
            .status(BidStatus.PENDING)
            .timestamp(LocalDateTime.now())
            .build();

    BidResponseDTO dto = mapper.toDto(bid);
    assertNotNull(dto);
    assertEquals(bid.getId(), dto.getId());
    assertEquals(chatId, dto.getChatId());
    assertEquals(200.0, dto.getPrice());
    assertEquals(BidStatus.PENDING, dto.getStatus());
    assertEquals(bid.getTimestamp(), dto.getTimestamp());
  }
}
