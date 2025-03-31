package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
  List<Message> findByChatOrderBySentAtDesc(Chat chat);
  List<Message> findByChatOrderBySentAtAsc(Chat chat);
  List<Message> findBySender(User sender);
  List<Message> findByChatAndSender(Chat chat, User sender);
 }
