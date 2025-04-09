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
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    /**
     * Retrieves a listing by its ID.
     * This method checks if the listing exists in the database and returns it.
     *
     * @param id the ID of the listing to retrieve
     * @return the listing with the specified ID
     */
    public Listing getListingById(UUID id) {
        logger.info("> Getting listing by id: {}", id);
        return listingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Listing with ID " + id + " not found."));
    }

    /**
     * Retrieves listings by their title.
     * This method checks if the listing exists in the database and returns it.
     *
     * @param title    the title of the listing to retrieve
     * @param pageable pagination information
     * @return a list of listings with the specified title
     */
    public List<Listing> getListingsByTitle(String title, Pageable pageable) {
        logger.info("> Getting listings by title: {}", title);
        List<Listing> listings = listingRepository.findByTitleContainingIgnoreCaseAndStatus(title, ListingStatus.ACTIVE, pageable);
        if (listings.isEmpty()) {
            throw new NoSuchElementException("No listings found with title " + title);
        }
        return listings;
    }

    /**
     * Retrieves listings by their seller.
     * This method checks if the listing exists in the database and returns it.
     *
     * @param seller   the seller of the listings to retrieve
     * @param pageable pagination information
     * @return a list of listings by the specified seller
     */
    public List<Listing> getListingsBySeller(User seller, Pageable pageable) {
        logger.info("> Getting listings by seller: {}", seller.getId());
        List<Listing> listings = listingRepository.findBySeller(seller, pageable);
        if (listings.isEmpty()) {
            throw new NoSuchElementException("No listings found for seller with ID " + seller.getId());
        }
        return listings;
    }

    /**
     * DOESN NOT WORK! Pagination issue. Use
     * <p>
     * Retrieves listings by their categories.
     * This method checks if the listing exists in the database and returns it.
     * Mainly used for filtering listings based on user search history.
     *
     * @param categoryList the list of categories to filter listings
     * @param pageable     pagination information
     * @return a list of listings by the specified categories
     */
    public List<Listing> findByCategories(List<Category> categoryList, Pageable pageable) {
        throw new NotImplementedException("This method does not work! Pagination issue accross multiple categories");
        //TODO: Fix at a later time.

//        logger.info("> Finding listings by categories: {}", categoryList);
//        if (categoryList == null || categoryList.isEmpty()) {
//            categoryList = categoryService.getAll();
//        }
//
//        int totalPageSize = pageable.getPageSize();
//        int categoryCount = categoryList.size();
//        int perCategorySize = (int) Math.ceil((double) totalPageSize / categoryCount);
//
//        List<Listing> combinedListings = new ArrayList<>();
//        for (Category category : categoryList) {
//            Pageable categoryPageable = PageRequest.of(pageable.getPageNumber(), perCategorySize, pageable.getSort());
//            List<Listing> listingsPerCategory = listingRepository.findByCategoryAndStatus(category, ListingStatus.ACTIVE, categoryPageable);
//
//            logger.info("> Category {} returned {} listings", category.getName(), listingsPerCategory.size());
//            combinedListings.addAll(listingsPerCategory);
//        }
//        if (combinedListings.size() > totalPageSize) {
//            logger.warn("> Combined listings size exceeds total page size, trimming to {}", totalPageSize);
//            combinedListings = combinedListings.subList(0, totalPageSize);
//        }
//        logger.info("> Combined listings count: {}", combinedListings.size());
//        return combinedListings;
    }

    public Page<Listing> findByCategories2(List<Category> categories, Pageable pageable) {
        if (categories == null || categories.isEmpty()) {
            categories = categoryService.getAll();
        }

        // Combine listings from all categories.
        List<Listing> combinedListings = new ArrayList<>();
        for (Category category : categories) {
            combinedListings.addAll(category.getListings());
        }

        // Shuffle the listings for randomization.
        Collections.shuffle(combinedListings);

        int pageSize = pageable.getPageSize();
        int totalListings = combinedListings.size();
        int requestedPageNumber = pageable.getPageNumber();
        int fromIndex = requestedPageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalListings);

        // If the requested page is out of bounds, set it to the last page.
        if (fromIndex >= totalListings && totalListings > 0) {
            int lastPage = (totalListings - 1) / pageSize;
            fromIndex = lastPage * pageSize;
            toIndex = Math.min(fromIndex + pageSize, totalListings);
        }

        List<Listing> pageContent = combinedListings.subList(fromIndex, toIndex);
        logger.info("> Combined listings count: {}", pageContent.size());

        return new PageImpl<>(pageContent, pageable, totalListings);
    }

    /**
     * Retrieves listings by their category name.
     * This method checks if the listing exists in the database and returns it.
     *
     * @param categoryName the name of the category to filter listings
     * @param pageable     pagination information
     * @return a list of listings by the specified category
     */
    public List<Listing> getListingsBySingleCategory(String categoryName, Pageable pageable) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name must be provided.");
        }
        Category category = categoryService.getByName(categoryName);
        return listingRepository.findByCategoryAndStatus(category, ListingStatus.ACTIVE, pageable);
    }


    /**
     * Creates a new listing.
     * This method validates the listing details and saves the listing to the database.
     * It also handles the image upload process.
     *
     * @param dto    the details of the listing to create
     * @param seller the user who is creating the listing
     * @param images the images for the listing
     * @return the created listing
     */
    @Transactional
    public Listing createListing(ListingCreationRequestDTO dto, User seller, MultipartFile[] images) throws IOException {
        logger.info("> Creating listing: {}", dto.getTitle());
        Listing listing = Listing.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(categoryService.getByName(dto.getCategoryName()))
                .status(dto.getListingStatus())
                .price(dto.getPrice())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .seller(seller)
                .build();
        validateListing(listing);
        logger.info("> Creating listing without images");

        listing = listingRepository.save(listing);
        addImagesToListing(listing.getId(), images);

        return listing;
    }

    /**
     * Updates an existing listing.
     * This method checks if the user is the seller of the listing before allowing any updates.
     * It also validates the listing details and updates the images if provided.
     *
     * @param id     the ID of the listing to update
     * @param user   the user who is trying to update the listing
     * @param dto    the new details for the listing
     * @param images the new images for the listing
     * @return the updated listing
     */
    public Listing updateListing(UUID id, User user, ListingCreationRequestDTO dto, MultipartFile[] images) throws IOException {
        logger.info("> Updating listing with ID: {}", id);

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Listing with ID " + id + " does not exist."));

        if (!listing.getSeller().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this listing.");
        }

        validateListing(listing);

        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setCategory(categoryService.getByName(dto.getCategoryName()));
        listing.setStatus(dto.getListingStatus() != null ? dto.getListingStatus() : ListingStatus.ACTIVE);
        listing.setPrice(dto.getPrice());
        listing.setLatitude(dto.getLatitude());
        listing.setLongitude(dto.getLongitude());

        if (dto.getImagesToDelete() != null) {
            removeImagesFromListing(listing, dto.getImagesToDelete());
        }

        if (images != null && images.length > 0) {
            addImagesToListing(listing.getId(), images);
        }

        return listingRepository.save(listing);
    }

    /**
     * Updates the status of a listing.
     * This method checks if the listing is already sold before allowing any status change.
     *
     * @param id     the ID of the listing
     * @param status the new status to set
     */
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


    /**
     * Deletes a listing by its ID.
     * This method checks if the user is the seller of the listing before deleting it.
     * It also deletes the images associated with the listing from the filesystem.
     *
     * @param id   the ID of the listing to delete
     * @param user the user who is trying to delete the listing
     */
    @Transactional
    public void deleteListingById(UUID id, User user) {
        logger.info("> Deleting listing with ID: {}", id);

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Listing with ID " + id + " does not exist."));

        if (!listing.getSeller().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this listing.");
        }

        // Delete images from disk
        Path imageDir = Paths.get("/app/uploads", id.toString());
        try {
            if (Files.exists(imageDir)) {
                Files.walk(imageDir)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                logger.info("> Deleted images from filesystem at: {}", imageDir);
            }
        } catch (IOException e) {
            logger.error("Error deleting image files for listing {}", id, e);
        }

        listingRepository.delete(listing);
    }

    /**
     * Adds images to a listing.
     * This method creates a directory for the listing if it doesn't exist,
     * saves the images to that directory,
     * and updates the listing with the new images.
     *
     * @param listingId the ID of the listing
     * @param images    the images to add
     */
    public void addImagesToListing(UUID listingId, MultipartFile[] images) {
        logger.info("> Adding image(s) to listing: {}", listingId);

        if (images == null || images.length == 0) return;

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing with ID " + listingId + " not found."));

        List<ListingImage> listingImages = new ArrayList<>();
        Path imageDir = Paths.get("/app/uploads", listingId.toString());

        try {
            Files.createDirectories(imageDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory for listing images", e);
        }

        for (MultipartFile image : images) {
            if (image.isEmpty()) continue;

            try {
                String originalName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                String fileName = UUID.randomUUID() + "_" + originalName;
                Path imagePath = imageDir.resolve(fileName);
                image.transferTo(imagePath.toFile());

                String imageUrl = "/images/" + listingId + "/" + fileName;

                ListingImage listingImage = ListingImage.builder()
                        .listing(listing)
                        .imageUrl(imageUrl)
                        .originalFileName(originalName)
                        .build();

                listingImages.add(listingImage);
            } catch (IOException e) {
                logger.error("Failed to save image for listing {}", listingId, e);
            }
        }

        listingImageRepository.saveAll(listingImages);
        listing.getImages().addAll(listingImages);
    }


    /**
     * Removes images from a listing.
     * This method deletes the images from the filesystem and removes them from the listing.
     *
     * @param listing                   the listing from which to remove images
     * @param originalFileNamesToDelete the list of original file names to delete
     */
    public void removeImagesFromListing(Listing listing, List<String> originalFileNamesToDelete) {
        logger.info("> Removing image(s) from listing {}", listing.getId());

        Path imageDir = Paths.get("/app/uploads", listing.getId().toString());
        Iterator<ListingImage> iterator = listing.getImages().iterator();

        while (iterator.hasNext()) {
            ListingImage image = iterator.next();
            String originalName = image.getOriginalFileName();

            if (originalFileNamesToDelete.contains(originalName)) {
                try {
                    Path imagePath = imageDir.resolve(Paths.get(image.getImageUrl()).getFileName());
                    Files.deleteIfExists(imagePath);
                    listingImageRepository.delete(image);
                    iterator.remove();
                    logger.info("> Deleted image: {}", image.getImageUrl());
                } catch (IOException e) {
                    logger.error("Failed to delete image: {}", image.getImageUrl(), e);
                }
            }
        }
    }

    /**
     * Validates the listing details.
     * This method checks if the title, description, price, category, latitude, and longitude are valid.
     *
     * @param listing the listing to validate
     */
    private void validateListing(Listing listing) {
        validateTitle(listing.getTitle());
        validateDescription(listing.getDescription());
        validatePrice(listing.getPrice());
        validateCategory(listing.getCategory());
        validateLatitude(listing.getLatitude());
        validateLongitude(listing.getLongitude());
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
        if (price == null || price < 0 || price > Double.MAX_VALUE) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or blank.");
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

