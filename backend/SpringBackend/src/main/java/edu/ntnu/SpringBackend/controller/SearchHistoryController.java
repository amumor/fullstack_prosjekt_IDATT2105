package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.SearchHistoryDTO;
import edu.ntnu.SpringBackend.dto.SearchHistoryListResponseDTO;
import edu.ntnu.SpringBackend.mapper.SearchHistoryMapper;
import edu.ntnu.SpringBackend.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final Logger logger = LoggerFactory.getLogger(SearchHistoryController.class);
    private final SearchHistoryMapper searchHistoryMapper;
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<SearchHistoryListResponseDTO> findByUserId(
            @PathVariable UUID userId
            ) {
        logger.info("GET Request received on [/api/v1/search-history/user-id/{}]", userId);
        return ResponseEntity.ok(searchHistoryMapper.toDto(searchHistoryService.findByUserId(userId)));
    }

    @PostMapping("/add")
    public ResponseEntity<SearchHistoryDTO> add(
            @RequestBody SearchHistoryDTO request
    ) {
        logger.info("POST Request received on [/api/v1/search-history/add]");
        return ResponseEntity.ok(searchHistoryMapper.toDto(searchHistoryService.add(request)));
    }
}
