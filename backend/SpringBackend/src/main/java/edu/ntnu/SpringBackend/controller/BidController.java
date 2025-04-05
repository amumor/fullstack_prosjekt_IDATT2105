package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.BidRequestDTO;
import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.mapper.BidMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.BidService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BidController {
  private final Logger logger = LoggerFactory.getLogger(BidController.class);
  private final BidService bidService;
  private final BidMapper bidMapper;

  @PostMapping("/chat/{chatId}/bids")
  public ResponseEntity<BidResponseDTO> placeBid(@PathVariable UUID chatId,
                                                 @AuthenticationPrincipal User user,
                                                 @RequestBody BidRequestDTO bidRequest) {
    return ResponseEntity.ok(bidMapper.toDto(bidService.placeBid(chatId, user, bidRequest.getPrice())));
  }

}
