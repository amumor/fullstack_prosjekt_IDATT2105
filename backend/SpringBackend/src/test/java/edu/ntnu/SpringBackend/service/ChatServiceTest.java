package edu.ntnu.SpringBackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.dto.MessageRequestDTO;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import edu.ntnu.SpringBackend.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.access.AccessDeniedException;

public class ChatServiceTest {

  @Mock
  private ChatRepository chatRepository;

  @Mock
  private MessageRepository messageRepository;

  @Mock
  private ListingService listingService;

  @InjectMocks
  private ChatService chatService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // getChatFromBuyerAndListing
  @Test
  public void testGetChatFromBuyerAndListing_Found() {
    UUID listingId = UUID.randomUUID();
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("buyer@example.com");
    Listing listing = new Listing();
    listing.setId(UUID.randomUUID());
    Chat chat = Chat.builder().build();
    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.findByBuyerAndListing(user, listing)).thenReturn(Optional.of(chat));

    Chat result = chatService.getChatFromBuyerAndListing(listingId, user);
    assertEquals(chat, result);
  }

  @Test
  public void testGetChatFromBuyerAndListing_NotFound() {
    UUID listingId = UUID.randomUUID();
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("buyer@example.com");
    Listing listing = new Listing();
    listing.setId(UUID.randomUUID());
    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.findByBuyerAndListing(user, listing)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> chatService.getChatFromBuyerAndListing(listingId, user));
  }

  // getAllChatsForListing
  @Test
  public void testGetAllChatsForListing_Success() {
    UUID listingId = UUID.randomUUID();
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    Listing listing = new Listing();
    listing.setId(listingId);
    listing.setSeller(seller);

    User user = seller;  // seller is the requestor
    List<Chat> chats = Arrays.asList(Chat.builder().build());
    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.findByListing(listing)).thenReturn(chats);

    List<Chat> result = chatService.getAllChatsForListing(listingId, user);
    assertEquals(chats, result);
  }

  @Test
  public void testGetAllChatsForListing_NotSeller() {
    UUID listingId = UUID.randomUUID();
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    Listing listing = new Listing();
    listing.setId(listingId);
    listing.setSeller(seller);

    User user = mock(User.class);
    when(user.getEmail()).thenReturn("other@example.com");
    when(listingService.getListingById(listingId)).thenReturn(listing);

    assertThrows(AccessDeniedException.class, () -> chatService.getAllChatsForListing(listingId, user));
  }

  @Test
  public void testGetAllChatsForListing_NoChatsFound() {
    UUID listingId = UUID.randomUUID();
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    Listing listing = new Listing();
    listing.setId(listingId);
    listing.setSeller(seller);

    User user = seller;
    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.findByListing(listing)).thenReturn(Collections.emptyList());

    assertThrows(NoSuchElementException.class, () -> chatService.getAllChatsForListing(listingId, user));
  }

  // getAllChatsForUser
  @Test
  public void testGetAllChatsForUser_Success() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");
    Chat chat = Chat.builder().build();
    List<Chat> chats = Arrays.asList(chat);
    when(chatRepository.findByBuyerOrListing_Seller(user, user)).thenReturn(chats);

    Collection<Chat> result = chatService.getAllChatsForUser(user);
    assertEquals(chats, result);
  }

  @Test
  public void testGetAllChatsForUser_NoChatsFound() {
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("user@example.com");
    when(chatRepository.findByBuyerOrListing_Seller(user, user)).thenReturn(Collections.emptyList());

    assertThrows(NoSuchElementException.class, () -> chatService.getAllChatsForUser(user));
  }

  // createChat
  @Test
  public void testCreateChat_InvalidInitialMessage() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    UUID listingId = UUID.randomUUID();
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("  ").build();

    assertThrows(IllegalArgumentException.class, () -> chatService.createChat(buyer, listingId, msgDTO));
  }

  @Test
  public void testCreateChat_ChatAlreadyExists() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    UUID listingId = UUID.randomUUID();
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("Hello").build();
    Listing listing = new Listing();
    listing.setId(listingId);
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    listing.setSeller(seller);

    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.existsByListingAndBuyer(listing, buyer)).thenReturn(true);

    assertThrows(IllegalArgumentException.class, () -> chatService.createChat(buyer, listingId, msgDTO));
  }

  @Test
  public void testCreateChat_BuyerIsSeller() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("seller@example.com");
    UUID listingId = UUID.randomUUID();
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("Hello").build();
    Listing listing = new Listing();
    listing.setId(listingId);
    listing.setSeller(buyer); // buyer and seller are the same
    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.existsByListingAndBuyer(listing, buyer)).thenReturn(false);

    assertThrows(AccessDeniedException.class, () -> chatService.createChat(buyer, listingId, msgDTO));
  }

  @Test
  public void testCreateChat_Success() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    UUID listingId = UUID.randomUUID();
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("Initial message").build();
    Listing listing = new Listing();
    listing.setId(listingId);
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    listing.setSeller(seller);

    when(listingService.getListingById(listingId)).thenReturn(listing);
    when(chatRepository.existsByListingAndBuyer(listing, buyer)).thenReturn(false);

    Chat chat = Chat.builder().id(UUID.randomUUID()).buyer(buyer).listing(listing).messages(new ArrayList<>()).build();
    when(chatRepository.save(any(Chat.class))).thenReturn(chat);
    when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));
    Message message = Message.builder().build();
    when(messageRepository.save(any(Message.class))).thenReturn(message);

    Chat result = chatService.createChat(buyer, listingId, msgDTO);
    assertEquals(chat, result);
  }

  // sendMessageToChat
  @Test
  public void testSendMessageToChat_ChatNotFound() {
    UUID chatId = UUID.randomUUID();
    User user = mock(User.class);
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("Hello").build();
    when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> chatService.sendMessageToChat(chatId, user, msgDTO));
  }

  @Test
  public void testSendMessageToChat_UserNotParticipant() {
    UUID chatId = UUID.randomUUID();
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("notparticipant@example.com");
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("Hello").build();

    Chat chat = Chat.builder().buyer(mock(User.class)).listing(new Listing()).build();
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    chat.setBuyer(buyer);
    Listing listing = new Listing();
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    listing.setSeller(seller);
    chat.setListing(listing);

    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));
    assertThrows(AccessDeniedException.class, () -> chatService.sendMessageToChat(chatId, user, msgDTO));
  }

  @Test
  public void testSendMessageToChat_Success() {
    UUID chatId = UUID.randomUUID();
    User user = mock(User.class);
    when(user.getEmail()).thenReturn("buyer@example.com");
    MessageRequestDTO msgDTO = MessageRequestDTO.builder().content("Hello").build();

    Chat chat = Chat.builder().buyer(user).listing(new Listing()).messages(new ArrayList<>()).build();
    Listing listing = new Listing();
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    listing.setSeller(seller);
    chat.setListing(listing);

    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));
    Message message = Message.builder().build();
    when(messageRepository.save(any(Message.class))).thenReturn(message);

    Message result = chatService.sendMessageToChat(chatId, user, msgDTO);
    assertEquals(message, result);
    assertFalse(chat.getMessages().isEmpty());
  }

  // isParticipant
  @Test
  public void testIsParticipant() {
    UUID chatId = UUID.randomUUID();
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");
    Listing listing = new Listing();
    listing.setSeller(seller);

    Chat chat = Chat.builder().buyer(buyer).listing(listing).build();
    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

    assertFalse(chatService.isParticipant(chatId, buyer));
    assertFalse(chatService.isParticipant(chatId, seller));

    User other = mock(User.class);
    when(other.getEmail()).thenReturn("other@example.com");
    assertTrue(chatService.isParticipant(chatId, other));
  }

  // getChatById
  @Test
  public void testGetChatById() {
    UUID chatId = UUID.randomUUID();
    Chat chat = Chat.builder().build();
    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));
    assertEquals(chat, chatService.getChatById(chatId));
  }

  @Test
  public void testGetChatById_NotFound() {
    UUID chatId = UUID.randomUUID();
    when(chatRepository.findById(chatId)).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> chatService.getChatById(chatId));
  }
}
