package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Message entity representing a message in a chat.
 * Contains information about the chat, sender, content, and timestamp.
 * This class is used to represent a message in the database.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

  /**
   * Unique identifier for the message.
   * Generated using UUID strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The chat associated with the message.
   * This is a many-to-one relationship with the Chat entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  /**
   * The user who sent the message.
   * This is a many-to-one relationship with the User entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  /**
   * The content of the message.
   * This field cannot be null and is stored as TEXT in the database.
   */
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  /**
   * The time when the message was sent.
   * This field is automatically set to the current time when the message is created.
   */
  @Column(nullable = false)
  private LocalDateTime sentAt;

  /**
   * The list of bids associated with the message.
   * This field is mapped by the "message" field in the Bid class.
   */
  @PrePersist
  protected void onCreate() {
    sentAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Message{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", sentAt=" + sentAt +
            '}';
  }
}