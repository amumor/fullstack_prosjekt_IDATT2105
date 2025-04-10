package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ListingCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.ListingListResponseDTO;
import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.mapper.ListingMapper;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.CategoryService;
import edu.ntnu.SpringBackend.service.ListingService;
import edu.ntnu.SpringBackend.service.SearchHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;
    private final Logger logger = LoggerFactory.getLogger(ListingController.class);
    private final ListingMapper listingMapper;
    private final CategoryService categoryService;
    private final SearchHistoryService searchHistoryService;

    /**
     * Get suggestions for listings based on the user's search history.
     * This endpoint retrieves a list of suggested listings for the authenticated user.
     * Uses pagination to limit the number of results returned.
     * Accessible to all users, logged in or not.
     *
     * @param user the authenticated user
     * @return a list of listing response DTOs
     */
    @GetMapping("/get-suggestions")
    @Operation(summary = "Get suggestions for listings based on search history", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ListingListResponseDTO> getSuggestions(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        final int MAX_SIZE = 100;
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
        logger.info("GET Request received on [/api/v1/listing/get-suggestions]");
        Pageable pageable = PageRequest.of(page, size);
        // Retrieve the search history of the user
        List<SearchHistory> searchHistory = searchHistoryService.findByUser(user);
        logger.info(" ---- Search history retrieved: {}", searchHistory.toString());

        // Retrieve categories based on the search history
        List<Category> categories = categoryService.findBySearchHistory(searchHistory);
        logger.info(" ---- Categories found: {}", categories.toString());

        // Find the listings using the categories and pageable
//        List<Listing> listings = listingService.findByCategories(categories, pageable); //TODO: fix this piece of shit
        List<Listing> listings = listingService.findByCategories2(categories, pageable).toList();

        logger.info(" ---- Listings retrieved: {}", listings.toString());
        for (Listing listing : listings) {
            logger.info(" ---- Listing: {}", listing.toString());
        }

        // Map the listings to a DTO
        var dto = listingMapper.toDto(listings);
        logger.info(" ---- Mapped DTO: {}", dto.toString());

        // Return the response with the mapped DTO
        return ResponseEntity.ok(dto);
    }

    /**
     * Get a specific listing by its ID.
     * This endpoint retrieves a listing by its unique identifier.
     * Accessible to all users, logged in or not.
     *
     * @param id the ID of the listing to retrieve
     * @return the listing response DTO
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get a listing by ID")
    public ResponseEntity<ListingResponseDTO> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET Request received on [/api/v1/listing/id/{}]", id);
        return ResponseEntity.ok(listingMapper.toDto(listingService.getListingById(id)));
    }

    /**
     * Get listings by title (search) with pagination.
     * Accessible to all users, logged in or not.
     *
     * @param title the title or keyword to search for
     * @param page  the page number (default 0)
     * @param size  the page size (default 10)
     * @return a list of listings matching the title search
     */
    @GetMapping("/get-by-title")
    @Operation(summary = "Get listings by title (search)")
    public ResponseEntity<ListingListResponseDTO> getByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        final int MAX_SIZE = 100;
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
        logger.info("GET Request received on [/api/v1/listing/get-by-title] with title: {}", title);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                listingMapper.toDto(
                        listingService.getListingsByTitle(title, pageable)
                )
        );
    }

    /**
     * Get listings for the authenticated seller with pagination.
     * This endpoint retrieves a list of listings created by the authenticated seller.
     * To view their own listings, the seller must be authenticated.
     *
     * @param user the authenticated seller
     * @param page the page number (default 0)
     * @param size the page size (default 10)
     * @return a list of listings created by the seller
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-by-seller")
    @Operation(summary = "Get listings by seller", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ListingListResponseDTO> getBySeller(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        final int MAX_SIZE = 100;
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
        logger.info("GET Request received on [/api/v1/listing/get-by-seller] for seller: {}", user.getId());
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                listingMapper.toDto(
                        listingService.getListingsBySeller(user, pageable)
                )
        );
    }

    /**
     * Get listings by single category with pagination.
     * Accessible to all users, logged in or not.
     *
     * @param categoryName the name of the category to filter by
     * @param page         the page number (default 0)
     * @param size         the page size (default 10)
     * @return a list of listings in the specified category
     */
    @GetMapping("/get-by-category")
    @Operation(summary = "Get listings by category")
    public ResponseEntity<ListingListResponseDTO> getByCategory(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        final int MAX_SIZE = 100;
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
        logger.info("GET Request received on [/api/v1/listing/get-by-category] with categoryName: {}", categoryName);
        Pageable pageable = PageRequest.of(page, size);
        List<Listing> listingsPage = listingService.getListingsBySingleCategory(categoryName, pageable);
        logger.info("> Retrieved {} listings for category: {}", listingsPage.size(), categoryName);

        var listingsDto = listingMapper.toDto(listingsPage);
        logger.info("> Mapping result: {}", listingsDto);

        return ResponseEntity.ok(listingsDto);    }


    /**
     * Create a new listing.
     * This endpoint allows the authenticated user to create a new listing.
     * Only authenticated users can create listings.
     *
     * @param user    the authenticated user creating the listing
     * @param request the request DTO containing listing details
     * @param images  array of images for the listing
     * @return the created listing response DTO
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @Operation(summary = "Create a new listing", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ListingResponseDTO> create(
            @AuthenticationPrincipal User user,
            @RequestPart("listing") ListingCreationRequestDTO request,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        logger.info("POST Request received on [/api/v1/listing/create]");
        logger.info("> Received {} image(s)", images != null ? images.length : 0);
        return ResponseEntity.ok(listingMapper.toDto(listingService.createListing(request, user, images)));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create-split")
    @Operation(summary = "Create a new listing, images handled by another endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ListingResponseDTO> createWithoutImage(
            @AuthenticationPrincipal User user,
            @RequestBody ListingCreationRequestDTO requestDTO
    ) {
        logger.info("POST Request received on [/api/v1/category/create-split]");
        try {
            var listing = listingService.createListing(requestDTO, user, null);
            return ResponseEntity.ok(listingMapper.toDto(listing));
        } catch (IOException e) {
            logger.error("Failed to create listing due to an IO exception", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Get all listings for the authenticated user.
     * This endpoint retrieves a list of all listings created by the authenticated user.
     * Only authenticated users can access this endpoint.
     *
     * @param user the authenticated user
     * @return a list of listing response DTOs
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a listing", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ListingResponseDTO> updateListing(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user,
            @RequestPart("listing") ListingCreationRequestDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        logger.info("PUT Request received on [/api/v1/listing/update/{}]", id);
        return ResponseEntity.ok(
                listingMapper.toDto(listingService.updateListing(id, user, dto, images))
        );
    }

    /**
     * Delete a listing by its ID.
     * This endpoint allows the authenticated user to delete a listing.
     * Only authenticated users can delete listings.
     *
     * @param user the authenticated user deleting the listing
     * @param id   the ID of the listing to delete
     * @return a response entity with no content
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a listing", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteListing(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("DELETE Request on [/api/v1/listing/delete/{}]", id);
        listingService.deleteListingById(id, user);
        return ResponseEntity.noContent().build();
    }
}
