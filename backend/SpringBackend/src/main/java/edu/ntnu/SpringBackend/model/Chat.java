package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Chat entity representing a chat between a buyer and a seller regarding a listing.
 * Contains information about the buyer, listing, creation time, messages, and bids.
 * The chat can include multiple messages and bids.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {

  /**
   * Unique identifier for the chat.
   * Generated using UUID strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The buyer involved in the chat.
   * This is a many-to-one relationship with the User entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  /**
   * The listing associated with the chat.
   * This is a many-to-one relationship with the Listing entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

  /**
   * The time when the chat was created.
   * This field is automatically set to the current time when the chat is created.
   */
  @Column(nullable = false)
  private LocalDateTime createdAt;

  /**
   * The list of messages associated with the chat.
   * This field is mapped by the "chat" field in the Message class.
   * It is automatically managed by JPA and will be removed if the chat is deleted.
   */
  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Message> messages = new ArrayList<>();

  /**
   * The list of bids associated with the chat.
   * This field is mapped by the "chat" field in the Bid class.
   * It is automatically managed by JPA and will be removed if the chat is deleted.
   */
  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Bid> bids = new ArrayList<>();

  /**
   * Method to set the creation time of the chat.
   * This method is called before the chat is persisted to the database.
   */
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "Chat{" +
            "id=" + id +
            ", buyer=" + buyer +
            ", listing=" + listing +
            ", createdAt=" + createdAt +
            ", messages=" + messages +
            ", bids=" + bids +
            '}';
  }
}