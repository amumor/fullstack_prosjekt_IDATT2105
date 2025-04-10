package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Message;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Message entities.
 * This interface extends JpaRepository to provide CRUD operations for Message entities.
 * It also includes custom query methods for finding messages by chat and sender.
 * <p>
 * JPA (Java Persistence API) is a specification for accessing, persisting,
 * and managing data between Java objects and relational databases.
 * It provides a standard way to map Java objects to database tables and vice versa.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
public interface MessageRepository extends JpaRepository<Message, UUID> {
  List<Message> findByChatOrderBySentAtDesc(Chat chat);

  List<Message> findByChatOrderBySentAtAsc(Chat chat);

  List<Message> findBySender(User sender);

  List<Message> findByChatAndSender(Chat chat, User sender);
}
