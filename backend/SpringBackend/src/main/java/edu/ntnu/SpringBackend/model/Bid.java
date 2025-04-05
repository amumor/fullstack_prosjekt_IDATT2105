package edu.ntnu.SpringBackend.model;

import edu.ntnu.SpringBackend.model.enums.BidStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bids")
public class Bid {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  @Column(nullable = false)
  private Double price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BidStatus status;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
    if (this.status == null) {
      this.status = BidStatus.PENDING;
    }
  }
}
