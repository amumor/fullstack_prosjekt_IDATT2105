package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ListingCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.ListingListResponseDTO;
import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.mapper.ListingMapper;
import edu.ntnu.SpringBackend.service.CategoryService;
import edu.ntnu.SpringBackend.service.ListingService;
import edu.ntnu.SpringBackend.service.SearchHistoryService;
import edu.ntnu.SpringBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-suggestions/{userId}")
    public ResponseEntity<ListingListResponseDTO> getSuggestions(
            @PathVariable UUID userId
    ) {
        logger.info("GET Request recieved on [/api/v1/get-suggestions]");
        return ResponseEntity.ok(
                listingMapper.toDto(
                        listingService.findByCategories(
                                categoryService.findBySearchHistory(
                                        searchHistoryService.findByUserId(userId)
                                )
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
            @RequestBody ListingCreationRequestDTO request
    ) {
        logger.info("Request recieved on [/api/v1/listing/create]");
        return ResponseEntity.ok(listingMapper.toDto(listingService.createListing(request)));
    }
}
