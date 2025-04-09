package edu.ntnu.SpringBackend.model;

import edu.ntnu.SpringBackend.model.enums.BidStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Bid entity representing a bid made by a user on a chat.
 * Contains information about the bid such as the buyer, price, status, and timestamp.
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
@Table(name = "bids")
public class Bid {

  /**
   * Unique identifier for the bid.
   * Generated using UUID strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The chat associated with the bid.
   * This is a many-to-one relationship with the Chat entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  /**
   * The user who made the bid.
   * This is a many-to-one relationship with the User entity.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  /**
   * The price of the bid.
   * This field is required and cannot be null.
   */
  @Column(nullable = false)
  private Double price;

  /**
   * The status of the bid.
   * This field indicates whether the bid is pending, accepted, or rejected.
   * It is an enum type BidStatus.
   * This field is required and cannot be null.
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BidStatus status;

  /**
   * The timestamp when the bid was created.
   * This field is automatically set to the current date and time when the bid is created.
   */
  @Column(nullable = false)
  private LocalDateTime timestamp;

  /**
   * This method is called before the entity is persisted to the database.
   * It sets the timestamp to the current date and time and the status to PENDING if it is null.
   */
  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
    if (this.status == null) {
      this.status = BidStatus.PENDING;
    }
  }
}
