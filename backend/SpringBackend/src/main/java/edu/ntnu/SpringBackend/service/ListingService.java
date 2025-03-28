package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
  private final ListingRepository listingRepository;

  public ListingService(ListingRepository listingRepository) {
    this.listingRepository = listingRepository;
  }

  public List<Listing> getAllListings() {
    return listingRepository.findAll();
  }

  public Optional<Listing> getListingById(int id) {
    return listingRepository.findById(id);
  }

  public List<Listing> getListingBySeller(User seller) {
    return listingRepository.findBySeller(seller);
  }


}
