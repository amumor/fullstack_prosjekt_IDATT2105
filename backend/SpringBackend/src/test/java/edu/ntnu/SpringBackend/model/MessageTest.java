package edu.ntnu.SpringBackend.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MessageTest {

  @Test
  public void testOnCreateSetsSentAt() {
    Message message = new Message();
    message.onCreate();
    assertNotNull(message.getSentAt(), "sentAt should be set by onCreate");
  }

  @Test
  public void testBuilderAndToString() {
    Chat chat = Mockito.mock(Chat.class);
    User sender = Mockito.mock(User.class);
    UUID id = UUID.randomUUID();
    String content = "Hello, test message";
    LocalDateTime now = LocalDateTime.now();

    Message message = Message.builder()
            .id(id)
            .chat(chat)
            .sender(sender)
            .content(content)
            .sentAt(now)
            .build();

    assertEquals(id, message.getId());
    assertEquals(chat, message.getChat());
    assertEquals(sender, message.getSender());
    assertEquals(content, message.getContent());
    assertEquals(now, message.getSentAt());
    assertTrue(message.toString().contains("Message{"));
  }
}
