package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.ListingCreationRequestDTO;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.ListingImage;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.repository.ListingImageRepository;
import edu.ntnu.SpringBackend.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

  private final Logger logger = LoggerFactory.getLogger(ListingService.class);
  private final CategoryService categoryService;
  private final ListingRepository listingRepository;
  private final ListingImageRepository listingImageRepository;

  public Listing getListingById(UUID id) {
    logger.info("> Getting listing by id: {}", id);
    return listingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Listing with ID " + id + " not found."));
  }

  public List<Listing> getListingBySeller(User seller) {
    logger.info("> Getting listings by seller: {}", seller.getId());
    if (seller.getId() == null) {
      throw new IllegalArgumentException("Invalid seller provided.");
    }

    List<Listing> listings = listingRepository.findBySeller(seller);
    if (listings.isEmpty()) {
      throw new NoSuchElementException("No listings found for seller with ID " + seller.getId());
    }
    return listings;
  }

  public List<Listing> getAllListings() {
    logger.info("> Getting all listings");
    List<Listing> all = listingRepository.findAll();
    if (all.isEmpty()) {
      throw new NoSuchElementException("No listings found.");
    }
    return all;
  }

  public List<Listing> findByCategories(List<Category> categoryList, Pageable pageable) {
    logger.info("> Finding listings by categories: {}", categoryList);
    if (categoryList == null || categoryList.isEmpty()) {
      return listingRepository.findByCategoryIn(categoryService.getAll(), pageable);
    }
    return listingRepository.findByCategoryIn(categoryList, pageable);
  }

  @Transactional
  public Listing createListing(ListingCreationRequestDTO dto, User seller, MultipartFile[] images) throws IOException {
    logger.info("> Creating listing: {}", dto.getTitle());
    Listing listing = Listing.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .category(categoryService.getByName(dto.getCategoryName()))
                    .status(ListingStatus.valueOf(dto.getListingStatus()))
                    .price(dto.getPrice())
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .seller(seller)
                    .build();
    validateListing(listing);
    logger.info("> Creating listing without images");

    if (listing.getStatus() == null || listing.getStatus() == ListingStatus.SOLD) {
      listing.setStatus(ListingStatus.ACTIVE);
    }
    listing = listingRepository.save(listing);
    addImagesToListing(listing.getId(), images);

    return listing;
  }

  public void addImagesToListing(UUID listingId, MultipartFile[] images) throws IOException {
    logger.info("> Saving image for listing");

    Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() -> new NoSuchElementException("Listing with ID " + listingId + " not found."));

    if (images == null || images.length == 0) {
      logger.info("> No images to add.");
      return;
    }

    List<ListingImage> listingImages = new ArrayList<>();
    logger.info("> Created {} images", images.length);

    for (MultipartFile image : images) {
      if (image.isEmpty()) continue;

      String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
      Path imageDir = Paths.get("/app/uploads", listingId.toString());
      Files.createDirectories(imageDir); // ensure directory exists

      Path imagePath = imageDir.resolve(fileName);
      image.transferTo(imagePath.toFile());

      String imageUrl = "/images/" + listingId + "/" + fileName;
      logger.info("> Saving image to: {}", imageUrl);

      ListingImage listingImage = ListingImage.builder()
              .listing(listing)
              .imageUrl(imageUrl)
              .build();

      listingImages.add(listingImage);
    }

    listingImageRepository.saveAll(listingImages);
    listing.getImages().addAll(listingImages);
  }


  @Transactional
  public Listing updateListing(UUID id, Listing updatedListing) {
    logger.info("> Updating listing with ID: {}", id);
    Listing existing = listingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Listing with ID " + id + " not found."));

    if (updatedListing.getTitle() != null) {
      validateTitle(updatedListing.getTitle());
      existing.setTitle(updatedListing.getTitle());
    }

    if (updatedListing.getDescription() != null) {
      validateDescription(updatedListing.getDescription());
      existing.setDescription(updatedListing.getDescription());
    }

    if (updatedListing.getPrice() != null) {
      validatePrice(updatedListing.getPrice());
      existing.setPrice(updatedListing.getPrice());
    }

    if (updatedListing.getCategory() != null) {
      existing.setCategory(updatedListing.getCategory());
    }

    if (updatedListing.getLatitude() != null) {
      validateLatitude(updatedListing.getLatitude());
      existing.setLatitude(updatedListing.getLatitude());
    }

    if (updatedListing.getLongitude() != null) {
      validateLongitude(updatedListing.getLongitude());
      existing.setLongitude(updatedListing.getLongitude());
    }

    return listingRepository.save(existing);
  }

  @Transactional
  public void deleteListingById(UUID id) {
    logger.info("> Deleting listing with ID: {}", id);
    if (!listingRepository.existsById(id)) {
      throw new NoSuchElementException("Listing with ID " + id + " does not exist.");
    }
    listingRepository.deleteById(id);
  }

  @Transactional
  public void updateListingStatus(UUID id, ListingStatus status) {
    logger.info("> Updating listing status for ID: {}", id);
    Listing listing = listingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Listing with ID " + id + " not found."));

    if (listing.getStatus() == ListingStatus.SOLD && status != ListingStatus.SOLD) {
      throw new IllegalStateException("Sold listings cannot be reactivated or changed.");
    }

    listing.setStatus(status);
    listingRepository.save(listing);
  }

  public void saveImagesForListing(UUID listingId) {}
  
  private void validateListing(Listing listing) {
    validateTitle(listing.getTitle());
    validateDescription(listing.getDescription());
    validatePrice(listing.getPrice());

    if (listing.getCategory() == null) {
      throw new IllegalArgumentException("Category must not be null.");
    }
  }

  private void validateTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
      throw new IllegalArgumentException("Title cannot be empty.");
    }
    if (title.length() > 100) {
      throw new IllegalArgumentException("Title cannot exceed 100 characters.");
    }
  }

  private void validateDescription(String description) {
    if (description == null || description.trim().length() < 10) {
      throw new IllegalArgumentException("Description must be at least 10 characters long.");
    }
  }

  private void validatePrice(Double price) {
    if (price == null || price <= 0) {
      throw new IllegalArgumentException("Price must be a positive number.");
    }
  }

  private void validateLatitude(Double latitude) {
    if (latitude == null || latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees.");
    }
  }

  private void validateLongitude(Double longitude) {
    if (longitude == null || longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees.");
    }
  }
}

