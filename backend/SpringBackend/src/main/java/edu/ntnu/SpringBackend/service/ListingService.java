package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Category;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
  private final ListingRepository listingRepository;

  public ListingService(ListingRepository listingRepository) {
    this.listingRepository = listingRepository;
  }

  public Optional<Listing> getListingById(int id) {
    return listingRepository.findById(id);
  }

  public List<Listing> getAllListings() {
    return listingRepository.findAll();
  }

  public List<Listing> getListingBySeller(User seller) {
    return listingRepository.findBySeller(seller);
  }

  public List<Listing> getListingByListingStatus(ListingStatus listingStatus) {
    return listingRepository.findByStatus(listingStatus);
  }

  public List<Listing> getListingByCategory(Category category) {
    return listingRepository.findByCategory(category);
  }

  @Transactional
  public Listing createListing(Listing listing) {
    return listingRepository.save(listing);
  }

  @Transactional
  public Optional<Listing> updateListing(int id, Listing updatedListing) {
    return listingRepository.findById(id)
            .map(existingListing -> {
              if (updatedListing.getTitle() != null) {
                existingListing.setTitle(updatedListing.getTitle());
              }
              if (updatedListing.getDescription() != null) {
                existingListing.setDescription(updatedListing.getDescription());
              }
              if (updatedListing.getPrice() != null) {
                existingListing.setPrice(updatedListing.getPrice());
              }
              if (updatedListing.getCategory() != null) {
                existingListing.setCategory(updatedListing.getCategory());
              }
              if (updatedListing.getLatitude() != null) {
                existingListing.setLatitude(updatedListing.getLatitude());
              }
              if (updatedListing.getLongitude() != null) {
                existingListing.setLongitude(updatedListing.getLongitude());
              }
              return listingRepository.save(existingListing);
            });
  }

  @Transactional
  public Optional<Listing> updateListingStatus(int id, ListingStatus listingStatus) {
    return listingRepository.findById(id)
            .map(existingListing -> {
              existingListing.setStatus(listingStatus);
              return listingRepository.save(existingListing);
            });
  }

  @Transactional
  public void deleteListingById(int id) {
    listingRepository.deleteById(id);
  }
}
