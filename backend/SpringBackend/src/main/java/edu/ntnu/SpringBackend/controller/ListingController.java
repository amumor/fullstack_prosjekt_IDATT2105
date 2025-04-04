package edu.ntnu.SpringBackend.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;
    private final Logger logger = LoggerFactory.getLogger(ListingController.class);
    private final ListingMapper listingMapper;
    private final CategoryService categoryService;
    private final UserService userService;
    private final SearchHistoryService searchHistoryService;

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
        logger.info("GET Request recieved on [/api/v1/listing/get-suggestions]");
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

    @GetMapping("/id/{id}")
    public ResponseEntity<ListingResponseDTO> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET Request received on [/api/v1/listing/id/{}]", id);
        return ResponseEntity.ok(listingMapper.toDto(listingService.getListingById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ListingResponseDTO> create(
            @AuthenticationPrincipal User user,
            @RequestBody ListingCreationRequestDTO request
    ) {
        logger.info("POST Request recieved on [/api/v1/listing/create]");
        return ResponseEntity.ok(listingMapper.toDto(listingService.createListing(request, user)));
    }
}
