package edu.ntnu.SpringBackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.dto.BidRequestDTO;
import edu.ntnu.SpringBackend.model.Bid;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.BidStatus;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.repository.BidRepository;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.access.AccessDeniedException;

public class BidServiceTest {

  @Mock
  private BidRepository bidRepository;

  @Mock
  private ChatRepository chatRepository;

  @Mock
  private ListingService listingService;

  @InjectMocks
  private BidService bidService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // Helper to create a valid Listing stub
  private Listing createListing(User seller, ListingStatus status) {
    Listing listing = mock(Listing.class);
    when(listing.getStatus()).thenReturn(status);
    when(listing.getSeller()).thenReturn(seller);
    when(listing.getId()).thenReturn(UUID.randomUUID());
    return listing;
  }

  // ------------------ getAcceptedBidsForUser ------------------

  @Test
  public void testGetAcceptedBidsForUser() {
    User user = mock(User.class);
    Bid acceptedBid = Bid.builder().id(UUID.randomUUID()).build();
    when(bidRepository.findByStatusAndBuyer(BidStatus.ACCEPTED, user))
            .thenReturn(java.util.Collections.singletonList(acceptedBid));
    assertEquals(1, bidService.getAcceptedBidsForUser(user).size());
  }

  // ------------------ placeBid ------------------

  @Test
  public void testPlaceBid_Success() {
    // Create buyer and seller mocks
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    // Create a valid Listing with ACTIVE status
    Listing listing = createListing(seller, ListingStatus.ACTIVE);

    // Create Chat using builder with buyer and listing
    Chat chat = Chat.builder()
            .buyer(buyer)
            .listing(listing)
            .build();

    // Stub chatRepository: when searching for the chat by its id, return our chat.
    UUID chatId = UUID.randomUUID();
    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

    // No pending bid exists for the chat
    when(bidRepository.findByChatAndStatus(chat, BidStatus.PENDING))
            .thenReturn(Optional.empty());

    // Simulate bidRepository.save returning a bid with an id set
    Bid savedBid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .buyer(buyer)
            .price(150.0)
            .status(BidStatus.PENDING)
            .build();
    when(bidRepository.save(any(Bid.class))).thenReturn(savedBid);

    BidRequestDTO bidRequest = BidRequestDTO.builder().price(150.0).build();
    Bid result = bidService.placeBid(chatId, buyer, bidRequest);

    assertNotNull(result);
    assertEquals(BidStatus.PENDING, result.getStatus());
    assertEquals(150.0, result.getPrice());
  }

  @Test
  public void testPlaceBid_PendingBidExists() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = createListing(seller, ListingStatus.ACTIVE);
    Chat chat = Chat.builder()
            .buyer(buyer)
            .listing(listing)
            .build();

    UUID chatId = UUID.randomUUID();
    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

    // Simulate an existing pending bid
    Bid existingBid = Bid.builder().id(UUID.randomUUID()).build();
    when(bidRepository.findByChatAndStatus(chat, BidStatus.PENDING))
            .thenReturn(Optional.of(existingBid));

    BidRequestDTO bidRequest = BidRequestDTO.builder().price(150.0).build();
    Exception ex = assertThrows(IllegalStateException.class, () ->
            bidService.placeBid(chatId, buyer, bidRequest));
    assertTrue(ex.getMessage().contains("Pending bid already exists"));
  }

  @Test
  public void testPlaceBid_InvalidListingStatus() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    // Create a Listing with non-ACTIVE status (e.g., SOLD)
    Listing listing = createListing(seller, ListingStatus.SOLD);
    Chat chat = Chat.builder()
            .buyer(buyer)
            .listing(listing)
            .build();

    UUID chatId = UUID.randomUUID();
    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

    BidRequestDTO bidRequest = BidRequestDTO.builder().price(150.0).build();
    Exception ex = assertThrows(IllegalStateException.class, () ->
            bidService.placeBid(chatId, buyer, bidRequest));
    assertTrue(ex.getMessage().contains("Listing is not active anymore"));
  }

  @Test
  public void testPlaceBid_UserNotBuyer() {
    User caller = mock(User.class);
    when(caller.getEmail()).thenReturn("intruder@example.com");

    // Actual buyer is different from the caller.
    User actualBuyer = mock(User.class);
    when(actualBuyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = createListing(seller, ListingStatus.ACTIVE);
    Chat chat = Chat.builder()
            .buyer(actualBuyer)
            .listing(listing)
            .build();

    UUID chatId = UUID.randomUUID();
    when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

    BidRequestDTO bidRequest = BidRequestDTO.builder().price(150.0).build();
    Exception ex = assertThrows(IllegalArgumentException.class, () ->
            bidService.placeBid(chatId, caller, bidRequest));
    assertTrue(ex.getMessage().contains("User is not the buyer of this chat"));
  }

  @Test
  public void testPlaceBid_ChatNotFound() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    UUID chatId = UUID.randomUUID();
    when(chatRepository.findById(chatId)).thenReturn(Optional.empty());

    BidRequestDTO bidRequest = BidRequestDTO.builder().price(150.0).build();
    Exception ex = assertThrows(IllegalArgumentException.class, () ->
            bidService.placeBid(chatId, buyer, bidRequest));
    assertTrue(ex.getMessage().contains("Chat not found"));
  }

  // ------------------ acceptBid ------------------

  @Test
  public void testAcceptBid_Success() {
    // Create buyer and seller
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = createListing(seller, ListingStatus.ACTIVE);

    // Create Chat and assign an ID so that validateBid can fetch it.
    Chat chat = Chat.builder()
            .buyer(buyer)
            .listing(listing)
            .build();
    UUID chatIdInBid = UUID.randomUUID();
    chat.setId(chatIdInBid);
    when(chatRepository.findById(chatIdInBid)).thenReturn(Optional.of(chat));

    Bid bid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .buyer(buyer)
            .price(200.0)
            .status(BidStatus.PENDING)
            .build();
    UUID bidId = bid.getId();
    when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));
    when(bidRepository.save(bid)).thenReturn(bid);
    doNothing().when(listingService).updateListingStatus(any(UUID.class), any(ListingStatus.class));

    Bid result = bidService.acceptBid(bidId, seller);
    assertEquals(BidStatus.ACCEPTED, result.getStatus());
    verify(bidRepository, times(1)).save(bid);
    verify(listingService, times(1)).updateListingStatus(listing.getId(), ListingStatus.SOLD);
  }

  @Test
  public void testAcceptBid_BidNotFound() {
    UUID bidId = UUID.randomUUID();
    when(bidRepository.findById(bidId)).thenReturn(Optional.empty());

    User seller = mock(User.class);
    Exception ex = assertThrows(IllegalArgumentException.class, () ->
            bidService.acceptBid(bidId, seller));
    assertTrue(ex.getMessage().contains("Bid not found"));
  }

  @Test
  public void testAcceptBid_InvalidUserSeller() {
    // Create a bid with a valid chat where seller is 'seller@example.com'
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User validSeller = mock(User.class);
    when(validSeller.getEmail()).thenReturn("seller@example.com");
    // Caller will be an invalid seller.
    User invalidSeller = mock(User.class);
    when(invalidSeller.getEmail()).thenReturn("intruder@example.com");

    Listing listing = createListing(validSeller, ListingStatus.ACTIVE);
    Chat chat = Chat.builder().buyer(buyer).listing(listing).build();
    UUID chatIdInBid = UUID.randomUUID();
    chat.setId(chatIdInBid);
    when(chatRepository.findById(chatIdInBid)).thenReturn(Optional.of(chat));

    Bid bid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .buyer(buyer)
            .price(200.0)
            .status(BidStatus.PENDING)
            .build();
    UUID bidId = bid.getId();
    when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));

    Exception ex = assertThrows(IllegalArgumentException.class, () ->
            bidService.acceptBid(bidId, invalidSeller));
    assertTrue(ex.getMessage().contains("User is not the seller of this listing"));
  }

  // ------------------ rejectBid ------------------

  @Test
  public void testRejectBid_Success() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = createListing(seller, ListingStatus.ACTIVE);
    Chat chat = Chat.builder().buyer(buyer).listing(listing).build();
    UUID chatIdInBid = UUID.randomUUID();
    chat.setId(chatIdInBid);
    when(chatRepository.findById(chatIdInBid)).thenReturn(Optional.of(chat));

    Bid bid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .buyer(buyer)
            .price(250.0)
            .status(BidStatus.PENDING)
            .build();
    UUID bidId = bid.getId();
    when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));
    when(bidRepository.save(bid)).thenReturn(bid);

    Bid result = bidService.rejectBid(bidId, seller);
    assertEquals(BidStatus.REJECTED, result.getStatus());
    verify(bidRepository, times(1)).save(bid);
  }

  // ------------------ cancelBid ------------------

  @Test
  public void testCancelBid_Success() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = createListing(seller, ListingStatus.ACTIVE);
    Chat chat = Chat.builder().buyer(buyer).listing(listing).build();
    UUID chatIdInBid = UUID.randomUUID();
    chat.setId(chatIdInBid);
    when(chatRepository.findById(chatIdInBid)).thenReturn(Optional.of(chat));

    Bid bid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .buyer(buyer)
            .price(300.0)
            .status(BidStatus.PENDING)
            .build();
    UUID bidId = bid.getId();
    when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));
    when(bidRepository.save(bid)).thenReturn(bid);

    Bid result = bidService.cancelBid(bidId, buyer);
    assertEquals(BidStatus.CANCELED, result.getStatus());
    verify(bidRepository, times(1)).save(bid);
  }

  @Test
  public void testCancelBid_NotPending() {
    User buyer = mock(User.class);
    when(buyer.getEmail()).thenReturn("buyer@example.com");
    User seller = mock(User.class);
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = createListing(seller, ListingStatus.ACTIVE);
    Chat chat = Chat.builder().buyer(buyer).listing(listing).build();
    UUID chatIdInBid = UUID.randomUUID();
    chat.setId(chatIdInBid);
    when(chatRepository.findById(chatIdInBid)).thenReturn(Optional.of(chat));

    // Create a bid that is not PENDING (e.g., ACCEPTED)
    Bid bid = Bid.builder()
            .id(UUID.randomUUID())
            .chat(chat)
            .buyer(buyer)
            .price(350.0)
            .status(BidStatus.ACCEPTED)
            .build();
    UUID bidId = bid.getId();
    when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));

    Exception ex = assertThrows(IllegalStateException.class, () ->
            bidService.cancelBid(bidId, buyer));
    assertTrue(ex.getMessage().contains("Bid is not pending anymore"));
  }
}
