package edu.ntnu.SpringBackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class MessageServiceTest {

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private MessageService messageService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllMessages() {
    List<Message> messages = Arrays.asList(Message.builder().build());
    when(messageRepository.findAll()).thenReturn(messages);
    assertEquals(messages, messageService.getAllMessages());
  }

  @Test
  public void testGetMessageById() {
    UUID id = UUID.randomUUID();
    Message message = Message.builder().build();
    when(messageRepository.findById(id)).thenReturn(Optional.of(message));
    Optional<Message> result = messageService.getMessageById(id);
    assertTrue(result.isPresent());
    assertEquals(message, result.get());
  }

  @Test
  public void testGetMessagesByChat() {
    Chat chat = Chat.builder().id(UUID.randomUUID()).build();
    List<Message> messages = Arrays.asList(Message.builder().build());
    when(messageRepository.findByChatOrderBySentAtAsc(chat)).thenReturn(messages);
    assertEquals(messages, messageService.getMessagesByChat(chat));
  }

  @Test
  public void testGetMessagesBySender() {
    User sender = User.builder().id(UUID.randomUUID()).build();
    List<Message> messages = Arrays.asList(Message.builder().build());
    when(messageRepository.findBySender(sender)).thenReturn(messages);
    assertEquals(messages, messageService.getMessagesBySender(sender));
  }

  @Test
  public void testAddMessage() {
    Message message = Message.builder()
        .id(UUID.randomUUID())
        .chat(Chat.builder().id(UUID.randomUUID()).build())
        .sender(User.builder().id(UUID.randomUUID()).build())
        .content("Test message")
            .build();
    when(messageRepository.save(message)).thenReturn(message);
    assertEquals(message, messageService.addMessage(message));
  }

  @Test
  public void testCreateMessage() {
    User sender = User.builder().build();
    Chat chat = Chat.builder().id(UUID.randomUUID()).build();
    String content = "New message";
    Message message = Message.builder().sender(sender).chat(chat).content(content).build();
    when(messageRepository.save(any(Message.class))).thenReturn(message);
    Message result = messageService.createMessage(sender, chat, content);
    assertNotNull(result);
    assertEquals(content, result.getContent());
  }

  @Test
  public void testUpdateMessage_Success() {
    UUID id = UUID.randomUUID();
    Message message = Message.builder().id(id).content("Old").build();
    when(messageRepository.findById(id)).thenReturn(Optional.of(message));
    when(messageRepository.save(message)).thenReturn(message);

    Optional<Message> result = messageService.updateMessage(id, "New content");
    assertTrue(result.isPresent());
    assertEquals("New content", result.get().getContent());
  }

  @Test
  public void testUpdateMessage_NotFound() {
    UUID id = UUID.randomUUID();
    when(messageRepository.findById(id)).thenReturn(Optional.empty());
    Optional<Message> result = messageService.updateMessage(id, "New content");
    assertFalse(result.isPresent());
  }

  @Test
  public void testDeleteMessageById() {
    UUID id = UUID.randomUUID();
    messageService.deleteMessageById(id);
    verify(messageRepository, times(1)).deleteById(id);
  }
}
