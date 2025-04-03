/*package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.CategoryRepository;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import edu.ntnu.SpringBackend.repository.ListingRepository;
import edu.ntnu.SpringBackend.repository.UserRepository;
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
public class ChatServiceTest {

  @Autowired private ChatService chatService;
  @Autowired private UserRepository userRepository;
  @Autowired private ListingRepository listingRepository;
  @Autowired private ChatRepository chatRepository;
  @Autowired private CategoryRepository categoryRepository;

  private User testBuyer;
  private User testSeller;
  private Listing testListing;
  private Chat testChat;
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
            .name("Test Category")
            .build());

    testListing = listingRepository.save(Listing.builder()
            .title("Test Listing")
            .description("Test description")
            .price(100.0)
            .category(testCategory)
            .status(ListingStatus.ACTIVE)
            .latitude(59.0)
            .longitude(10.0)
            .seller(testSeller)
            .build());

    testChat = Chat.builder()
            .buyer(testBuyer)
            .listing(testListing)
            .createdAt(LocalDateTime.now())
            .build();

    chatRepository.save(testChat);
  }

  @Test
  void getAllChats() {
    List<Chat> allChats = chatService.getAllChats();

    assertThat(allChats).isNotEmpty();
    assertThat(allChats.size()).isGreaterThanOrEqualTo(1);
  }

  @Test
  void getChatById() {
    Optional<Chat> retrievedChat = chatService.getChatById(testChat.getId());

    assertThat(retrievedChat).isPresent();
    assertThat(retrievedChat.get().getId()).isEqualTo(testChat.getId());
    assertThat(retrievedChat.get().getBuyer().getId()).isEqualTo(testBuyer.getId());
    assertThat(retrievedChat.get().getListing().getId()).isEqualTo(testListing.getId());
    assertThat(retrievedChat.get().getListing().getSeller().getId()).isEqualTo(testSeller.getId());
  }

  @Test
  void getChatById_NotFound() {
    Optional<Chat> retrievedChat = chatService.getChatById(UUID.randomUUID());

    assertThat(retrievedChat).isEmpty();
  }

  @Test
  void getChatsByBuyer() {
    List<Chat> buyerChats = chatService.getChatsByBuyer(testBuyer);

    assertThat(buyerChats).hasSize(1);
    assertThat(buyerChats.getFirst().getBuyer().getId()).isEqualTo(testBuyer.getId());
    assertThat(buyerChats.getFirst().getListing().getId()).isEqualTo(testListing.getId());
  }

  @Test
  void getChatsByListing() {
    List<Chat> listingChats = chatService.getChatsByListing(testListing);

    assertThat(listingChats).hasSize(1);
    assertThat(listingChats.getFirst().getListing().getId()).isEqualTo(testListing.getId());
    assertThat(listingChats.getFirst().getBuyer().getId()).isEqualTo(testBuyer.getId());
  }

  @Test
  void getChatByUserAndListing() {
    Optional<Chat> chat = chatService.getChatByUserAndListing(testBuyer, testListing);

    assertThat(chat).isPresent();
    assertThat(chat.get().getBuyer().getId()).isEqualTo(testBuyer.getId());
    assertThat(chat.get().getListing().getId()).isEqualTo(testListing.getId());
  }

  @Test
  void getChatByUserAndListing_NotFound() {
    User otherUser = userRepository.save(User.builder()
            .email("other@example.com")
            .password("Password123")
            .firstName("Other")
            .lastName("User")
            .phoneNumber("555555555")
            .role(Role.USER)
            .build());

    Optional<Chat> chat = chatService.getChatByUserAndListing(otherUser, testListing);

    assertThat(chat).isEmpty();
  }

  @Test
  void addChat() {
    Listing newListing2 = listingRepository.save(Listing.builder()
            .seller(testSeller)
            .title("New Listing")
            .description("New description")
            .price(200.0)
            .category(testCategory)
            .status(ListingStatus.ACTIVE)
            .build());

    Chat savedChat = chatService.createChat(testBuyer, newListing2);

    assertThat(savedChat.getCreatedAt()).isNotNull();
    assertThat(savedChat.getBuyer().getId()).isEqualTo(testBuyer.getId());
    assertThat(savedChat.getListing().getId()).isEqualTo(newListing2.getId());
  }

  @Test
  void createChat_Success() {
    Listing newListing = listingRepository.save(Listing.builder()
            .title("Another Listing")
            .description("Another description")
            .price(300.0)
            .category(testCategory)
            .status(ListingStatus.ACTIVE)
            .seller(testSeller)
            .build());

    Chat createdChat = chatService.createChat(testBuyer, newListing);

    assertThat(createdChat.getCreatedAt()).isNotNull();
    assertThat(createdChat.getBuyer().getId()).isEqualTo(testBuyer.getId());
    assertThat(createdChat.getListing().getId()).isEqualTo(newListing.getId());
  }

  @Test
  void createChat_AlreadyExists() {
    Chat existingChat = chatService.createChat(testBuyer, testListing);
    assertThat(existingChat.getId()).isEqualTo(testChat.getId());
  }

  @Test
  void deleteChatById() {
    UUID chatId = testChat.getId();
    assertThat(chatService.getChatById(chatId)).isPresent();

    chatService.deleteChatById(chatId);
    assertThat(chatService.getChatById(chatId)).isEmpty();
  }
}*/