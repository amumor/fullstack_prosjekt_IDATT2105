package edu.ntnu.SpringBackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.SpringBackend.dto.ListingCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.ListingListResponseDTO;
import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.mapper.ListingMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.CategoryService;
import edu.ntnu.SpringBackend.service.ListingService;
import edu.ntnu.SpringBackend.service.SearchHistoryService;
import edu.ntnu.SpringBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
     *
     * @param user the authenticated user
     * @return a list of listing response DTOs
     */
    @GetMapping("/get-suggestions")
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
        return ResponseEntity.ok(
                listingMapper.toDto(
                        listingService.findByCategories(
                                categoryService.findBySearchHistory(
                                        searchHistoryService.findByUser(user)
                                ), pageable
                        )
                )
        );
    }

    /**
     * Get a specific listing by its ID.
     * This endpoint retrieves a listing by its unique identifier.
     *
     * @param id the ID of the listing to retrieve
     * @return the listing response DTO
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ListingResponseDTO> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET Request received on [/api/v1/listing/id/{}]", id);
        return ResponseEntity.ok(listingMapper.toDto(listingService.getListingById(id)));
    }

    @GetMapping("/get-by-title")
    public ResponseEntity<ListingListResponseDTO> getByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        final int MAX_SIZE = 100;
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
        logger.info("GET Request received on [/api/v1/listing/get-by-title]");
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                listingMapper.toDto(
                        listingService.getListingByTitle(title)
                )
        );
    }


    /**
     * Create a new listing.
     * This endpoint allows the authenticated user to create a new listing.
     *
     * @param user the authenticated user creating the listing
     * @param request the request DTO containing listing details
     * @param images array of images for the listing
     * @return the created listing response DTO
     */
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<ListingResponseDTO> create(
            @AuthenticationPrincipal User user,
            @RequestPart("listing") ListingCreationRequestDTO request,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {
        logger.info("POST Request received on [/api/v1/listing/create]");
        logger.info("> Received {} image(s)", images != null ? images.length : 0);
        return ResponseEntity.ok(listingMapper.toDto(listingService.createListing(request, user, images)));
    }

    /**
     * Get all listings for the authenticated user.
     * This endpoint retrieves a list of all listings created by the authenticated user.
     *
     * @param user the authenticated user
     * @return a list of listing response DTOs
     */
    @PutMapping("/update/{id}")
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
     *
     * @param user the authenticated user deleting the listing
     * @param id the ID of the listing to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("DELETE Request on [/api/v1/listing/delete/{}]", id);
        listingService.deleteListingById(id, user);
        return ResponseEntity.noContent().build();
    }

}
