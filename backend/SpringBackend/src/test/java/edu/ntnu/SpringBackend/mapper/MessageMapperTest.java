package edu.ntnu.SpringBackend.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.dto.MessageResponseDTO;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MessageMapperTest {

  @Test
  public void testToDto() {
    MessageMapper mapper = new MessageMapper();

    Chat chat = mock(Chat.class);
    UUID chatId = UUID.randomUUID();
    when(chat.getId()).thenReturn(chatId);

    User sender = mock(User.class);
    when(sender.getFirstName()).thenReturn("John");
    when(sender.getLastName()).thenReturn("Doe");

    LocalDateTime sentAt = LocalDateTime.now();
    String content = "Test message";
    Message message = Message.builder()
            .chat(chat)
            .sender(sender)
            .content(content)
            .sentAt(sentAt)
            .build();

    MessageResponseDTO dto = mapper.toDto(message);
    assertNotNull(dto);
    assertEquals(chatId, dto.getChatId());
    assertEquals("John", dto.getSenderFirstName());
    assertEquals("Doe", dto.getSenderLastName());
    assertEquals(content, dto.getContent());
    assertEquals(sentAt, dto.getSentAt());
  }
}
