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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", nullable = false)
  private User buyer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "listing_id", nullable = false)
  private Listing listing;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Message> messages = new ArrayList<>();

  @OneToMany(mappedBy="chat")
  private List<Bid> bids;

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
            '}';
  }
}