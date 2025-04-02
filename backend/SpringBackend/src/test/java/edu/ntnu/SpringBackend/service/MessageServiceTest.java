package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class MessageServiceTest {
  @Autowired private MessageService messageService;
  @Autowired private MessageRepository messageRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private ListingRepository listingRepository;
  @Autowired private ChatRepository chatRepository;
  @Autowired private CategoryRepository categoryRepository;

  private User testBuyer;
  private User testSeller;
  private Listing testListing;
  private Chat testChat;
  private Message testMessage;
  private Category testCategory;

  @BeforeEach
  void setUp() {
    testBuyer = userRepository.save(User.builder()
            .email("buyer@example.com")
            .password("Password123")
            .firstName("Test")
            .lastName("Buyer")
            .phoneNumber("123456789")
            .role(Role.USER)
            .build());

    testSeller = userRepository.save(User.builder()
            .email("seller@example.com")
            .password("Password123")
            .firstName("Test")
            .lastName("Seller")
            .phoneNumber("987654321")
            .role(Role.USER)
            .build());

    testCategory = categoryRepository.save(Category.builder()
            .name("FURNITURE")
            .build());

    testListing = listingRepository.save(Listing.builder()
            .title("Test Listing")
            .description("Test description")
            .price(40.0)
            .category(testCategory)
            .status(ListingStatus.ACTIVE)
            .seller(testSeller)
            .build());

    testChat = chatRepository.save(Chat.builder()
            .buyer(testBuyer)
            .listing(testListing)
            .createdAt(LocalDateTime.now())
            .build());

    testMessage = messageRepository.save(Message.builder()
            .sender(testBuyer)
            .chat(testChat)
            .content("Hello, is this item still available?")
            .sentAt(LocalDateTime.now())
            .build());
  }

  @Test
  void getAllMessages() {
    List<Message> messages = messageService.getAllMessages();

    assertThat(messages).isNotEmpty();
    assertThat(messages.size()).isEqualTo(1);
  }

  @Test
  void getMessageById() {
    Optional<Message> retrievedMessage = messageService.getMessageById(testMessage.getId());

    assertThat(retrievedMessage).isPresent();
    assertThat(retrievedMessage.get().getId()).isEqualTo(testMessage.getId());
    assertThat(retrievedMessage.get().getContent()).isEqualTo("Hello, is this item still available?");
    assertThat(retrievedMessage.get().getSender().getId()).isEqualTo(testBuyer.getId());
    assertThat(retrievedMessage.get().getChat().getId()).isEqualTo(testChat.getId());
  }

  @Test
  void getMessageById_NotFound() {
    Optional<Message> retrievedMessage = messageService.getMessageById(UUID.randomUUID());

    assertThat(retrievedMessage).isEmpty();
  }

  @Test
  void getMessagesByChat() {
    messageRepository.save(Message.builder()
            .sender(testSeller)
            .chat(testChat)
            .content("Yes, it's still available.")
            .sentAt(LocalDateTime.now().plusMinutes(5))
            .build());

    List<Message> chatMessages = messageService.getMessagesByChat(testChat);

    assertThat(chatMessages).hasSize(2);
    // Messages should be sorted by sentAt
    assertThat(chatMessages.get(0).getContent()).isEqualTo("Hello, is this item still available?");
    assertThat(chatMessages.get(1).getContent()).isEqualTo("Yes, it's still available.");
  }

  @Test
  void getMessagesBySender() {
    messageRepository.save(Message.builder()
            .sender(testBuyer)
            .chat(testChat)
            .content("What's the condition?")
            .sentAt(LocalDateTime.now().plusMinutes(10))
            .build());

    List<Message> senderMessages = messageService.getMessagesBySender(testBuyer);

    assertThat(senderMessages).hasSize(2);
    assertThat(senderMessages.stream().map(Message::getSender)
            .allMatch(sender -> sender.equals(testBuyer))).isTrue();
  }

  @Test
  void addMessage() {
    Message newMessage = Message.builder()
            .sender(testSeller)
            .chat(testChat)
            .content("When would you like to pick it up?")
            .build();

    Message savedMessage = messageService.addMessage(newMessage);

    assertThat(savedMessage.getSentAt()).isNotNull();
    assertThat(savedMessage.getContent()).isEqualTo("When would you like to pick it up?");
    assertThat(savedMessage.getSender().getId()).isEqualTo(testSeller.getId());
    assertThat(savedMessage.getChat().getId()).isEqualTo(testChat.getId());
  }

  @Test
  void createMessage() {
    Message createdMessage = messageService.createMessage(
            testSeller,
            testChat,
            "Are you still interested?"
    );

    assertThat(createdMessage.getSentAt()).isNotNull();
    assertThat(createdMessage.getContent()).isEqualTo("Are you still interested?");
    assertThat(createdMessage.getSender().getId()).isEqualTo(testSeller.getId());
    assertThat(createdMessage.getChat().getId()).isEqualTo(testChat.getId());
  }

  @Test
  void updateMessage() {
    Optional<Message> updatedMessageOpt = messageService.updateMessage(
            testMessage.getId(),
            "Updated message content"
    );

    assertThat(updatedMessageOpt).isPresent();
    Message updatedMessage = updatedMessageOpt.get();
    assertThat(updatedMessage.getId()).isEqualTo(testMessage.getId());
    assertThat(updatedMessage.getContent()).isEqualTo("Updated message content");

    // Verify persistence by retrieving again
    Optional<Message> retrievedMessage = messageService.getMessageById(testMessage.getId());
    assertThat(retrievedMessage).isPresent();
    assertThat(retrievedMessage.get().getContent()).isEqualTo("Updated message content");
  }

  @Test
  void updateMessage_NotFound() {
    Optional<Message> updatedMessage = messageService.updateMessage(UUID.randomUUID(), "This message doesn't exist");

    assertThat(updatedMessage).isEmpty();
  }

  @Test
  void deleteMessageById() {
    UUID messageId = testMessage.getId();
    assertThat(messageService.getMessageById(messageId)).isPresent();

    messageService.deleteMessageById(messageId);
    assertThat(messageService.getMessageById(messageId)).isEmpty();
  }
}