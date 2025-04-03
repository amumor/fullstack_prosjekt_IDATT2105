package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Todo: Maybe delete this class and use the MessageRepository directly in chatService
@Service
public class MessageService {
  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  public Optional<Message> getMessageById(UUID id) {
    return messageRepository.findById(id);
  }

  public List<Message> getMessagesByChat(Chat chat) {
    return messageRepository.findByChatOrderBySentAtAsc(chat);
  }

  public List<Message> getMessagesBySender(User sender) {
    return messageRepository.findBySender(sender);
  }

  @Transactional
  public Message addMessage(Message message) {
    return messageRepository.save(message);
  }

  @Transactional
  public Message createMessage(User sender, Chat chat, String content) {
    Message message = Message.builder()
            .sender(sender)
            .chat(chat)
            .content(content)
            .build();
    return messageRepository.save(message);
  }

  @Transactional
  public Optional<Message> updateMessage(UUID id, String newContent) {
    return messageRepository.findById(id)
            .map(existingMessage -> {
              existingMessage.setContent(newContent);
              return messageRepository.save(existingMessage);
            });
  }

  @Transactional
  public void deleteMessageById(UUID id) {
    messageRepository.deleteById(id);
  }
}