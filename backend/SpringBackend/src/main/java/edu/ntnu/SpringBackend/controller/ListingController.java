package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;
    private final Logger logger = LoggerFactory.getLogger(ListingController.class);

    @GetMapping("/get-suggestions")
    public ResponseEntity<ListingResponseDTO> getSuggestions(
            @RequestHeader("Authorization") String autherizationHeader
    ) {
        logger.info("GET Request recieved on [/api/v1/get-suggestions]");
        //return ResponseEntity.ok(listingService.getSuggestions(autherizationHeader));
        throw new NotImplementedException();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ListingResponseDTO> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET Request received on [/api/v1/listing/id/{}]", id);
        return ResponseEntity.ok(listingService.getListingById(id));
    }
}
