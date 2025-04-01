package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
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

//    @GetMapping("/get-listing-by-id")
}
