package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final Logger logger = LoggerFactory.getLogger(MessageService.class);

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<Message> getAllMessages() {
    logger.info("> Getting all messages");
    return messageRepository.findAll();
  }

  public Optional<Message> getMessageById(UUID id) {
    logger.info("> Finding messages by id: {}", id);
    return messageRepository.findById(id);
  }

  public List<Message> getMessagesByChat(Chat chat) {
    logger.info("> Finding messages by chat: {}", chat.getId());
    return messageRepository.findByChatOrderBySentAtAsc(chat);
  }

  public List<Message> getMessagesBySender(User sender) {
    logger.info("> Finding message by sender: {}", sender.getId());
    return messageRepository.findBySender(sender);
  }

  @Transactional
  public Message addMessage(Message message) {
    logger.info("> Adding message to chat: {}", message.getChat().getId());
    return messageRepository.save(message);
  }

  @Transactional
  public Message createMessage(User sender, Chat chat, String content) {
    logger.info("> Creating message");
    Message message = Message.builder()
            .sender(sender)
            .chat(chat)
            .content(content)
            .build();
    return messageRepository.save(message);
  }

  @Transactional
  public Optional<Message> updateMessage(UUID id, String newContent) {
    logger.info("> Updating message: {}", id);
    return messageRepository.findById(id)
            .map(existingMessage -> {
              existingMessage.setContent(newContent);
              return messageRepository.save(existingMessage);
            });
  }

  @Transactional
  public void deleteMessageById(UUID id) {
    logger.info("> Deleting message: {}", id);
    messageRepository.deleteById(id);
  }
}