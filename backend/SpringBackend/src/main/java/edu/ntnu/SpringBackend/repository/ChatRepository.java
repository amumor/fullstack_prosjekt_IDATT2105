package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Chat;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
  List<Chat> findByBuyer(User buyer);
  List<Chat> findByListing(Listing listing);
}
