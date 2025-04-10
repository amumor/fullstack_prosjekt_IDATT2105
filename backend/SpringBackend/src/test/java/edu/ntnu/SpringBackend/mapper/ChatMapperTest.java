package edu.ntnu.SpringBackend.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.ntnu.SpringBackend.dto.ChatResponseDTO;
import edu.ntnu.SpringBackend.dto.MessageResponseDTO;
import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.Bid;
import edu.ntnu.SpringBackend.model.User;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.Test;

public class ChatMapperTest {

  @Test
  public void testToDto() {
    // Create mocks for dependent mappers.
    MessageMapper messageMapper = mock(MessageMapper.class);
    BidMapper bidMapper = mock(BidMapper.class);
    ChatMapper chatMapper = new ChatMapper(messageMapper, bidMapper);

    UUID chatId = UUID.randomUUID();
    UUID listingId = UUID.randomUUID();

    User buyer = mock(User.class);
    when(buyer.getFirstName()).thenReturn("BuyerFirst");
    when(buyer.getLastName()).thenReturn("BuyerLast");
    when(buyer.getEmail()).thenReturn("buyer@example.com");

    User seller = mock(User.class);
    when(seller.getFirstName()).thenReturn("SellerFirst");
    when(seller.getLastName()).thenReturn("SellerLast");
    when(seller.getEmail()).thenReturn("seller@example.com");

    Listing listing = new Listing();
    listing.setId(listingId);
    listing.setSeller(seller);

    LocalDateTime createdAt = LocalDateTime.now();

    Message message = Message.builder().build();
    Bid bid = Bid.builder().build();

    MessageResponseDTO msgDto = MessageResponseDTO.builder().build();
    BidResponseDTO bidDto = BidResponseDTO.builder().build();

    when(messageMapper.toDto(message)).thenReturn(msgDto);
    when(bidMapper.toDto(bid)).thenReturn(bidDto);

    List<Message> messages = Collections.singletonList(message);
    List<Bid> bids = Collections.singletonList(bid);

    Chat chat = Chat.builder()
            .id(chatId)
            .buyer(buyer)
            .listing(listing)
            .createdAt(createdAt)
            .messages(messages)
            .bids(bids)
            .build();

    ChatResponseDTO dto = chatMapper.toDto(chat);

    assertNotNull(dto);
    assertEquals(chatId, dto.getId());
    assertEquals(listingId, dto.getListingId());
    assertEquals("BuyerFirst", dto.getBuyerFirstName());
    assertEquals("BuyerLast", dto.getBuyerLastName());
    assertEquals("SellerFirst", dto.getSellerFirstName());
    assertEquals("SellerLast", dto.getSellerLastName());
    assertEquals(createdAt, dto.getCreatedAt());
    assertEquals(1, dto.getMessages().size());
    assertEquals(msgDto, dto.getMessages().get(0));
    assertEquals(1, dto.getBids().size());
    assertEquals(bidDto, dto.getBids().get(0));
  }
}
