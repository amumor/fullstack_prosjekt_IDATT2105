package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {
  private final ChatRepository chatRepository;

  public ChatService(ChatRepository chatRepository) {
    this.chatRepository = chatRepository;
  }

  public List<Chat> getAllChats() {
    return chatRepository.findAll();
  }

  public Optional<Chat> getChatById(UUID id) {
    return chatRepository.findById(id);
  }

  public List<Chat> getChatsByBuyer(User user) {
    return chatRepository.findByBuyer(user);
  }

  public List<Chat> getChatsByListing(Listing listing) {
    return chatRepository.findByListing(listing);
  }

  public Optional<Chat> getChatByUserAndListing(User firstUser, Listing listing) {
    return chatRepository.findByBuyerAndListing(
            firstUser, listing);
  }

  @Transactional
  public Chat createChat(User buyer, Listing listing) {
    // Check if a chat already exists between the buyer for this listing
    Optional<Chat> existingChat = getChatByUserAndListing(buyer, listing);
    if (existingChat.isPresent()) {
      return existingChat.get();
    }

    Chat chat = Chat.builder()
            .buyer(buyer)
            .listing(listing)
            .createdAt(LocalDateTime.now())
            .build();

    return chatRepository.save(chat);
  }

  @Transactional
  public void deleteChatById(UUID id) {
    chatRepository.deleteById(id);
  }
}