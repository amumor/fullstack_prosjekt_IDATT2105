package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.BidRequestDTO;
import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.mapper.BidMapper;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller class for handling bid-related requests.
 * This class contains methods for placing, accepting, rejecting, and canceling bids.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BidController {
  private final Logger logger = LoggerFactory.getLogger(BidController.class);
  private final BidService bidService;
  private final BidMapper bidMapper;

  /**
   * Get all bids for a user.
   * Only authenticated users can get their bids.
   *
   * @param user the user who is getting the bids
   * @return the list of bids
   */
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/bids/accepted")
  @Operation(summary = "Get all accepted bids for a user", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<List<BidResponseDTO>> getAcceptedBids(
          @AuthenticationPrincipal User user
  ) {
    logger.info("Received GET request on [/api/v1/bids/accepted]");
    return ResponseEntity.ok(bidService.getAcceptedBidsForUser(user).stream()
            .map(bidMapper::toDto)
            .collect(Collectors.toList()));
  }

  /**
   * Place a bid on a chat.
   * Only authenticated users can place a bid.
   *
   * @param chatId     the id of the chat
   * @param user       the user who is placing the bid
   * @param bidRequest the bid request
   * @return the bid response
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/chat/{chatId}/bids")
  @Operation(summary = "Place a bid on a chat", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BidResponseDTO> placeBid(
          @PathVariable UUID chatId,
          @AuthenticationPrincipal User user,
          @Valid @RequestBody BidRequestDTO bidRequest
  ) {
    logger.info("Received POST request on [/api/v1/chat/{}/bids]", chatId);
    return ResponseEntity.ok(bidMapper.toDto(bidService.placeBid(chatId, user, bidRequest)));
  }

  /**
   * Accept a bid.
   * Only the seller of the listing can accept a bid.
   *
   * @param bidId the id of the bid
   * @param user  the user who is accepting the bid
   * @return the bid response
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/bids/{bidId}/accept")
  @Operation(summary = "Accept a bid", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BidResponseDTO> acceptBid(
          @PathVariable UUID bidId,
          @AuthenticationPrincipal User user
  ) {
    logger.info("Received POST request on [/api/v1/bids/{}/accept]", bidId);
    return ResponseEntity.ok(bidMapper.toDto(bidService.acceptBid(bidId, user)));
  }

  /**
   * Reject a bid.
   * Only the seller of the listing can reject a bid.
   *
   * @param bidId the id of the bid
   * @param user  the user who is rejecting the bid
   * @return the bid response
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/bids/{bidId}/reject")
  @Operation(summary = "Reject a bid", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BidResponseDTO> rejectBid(
          @PathVariable UUID bidId,
          @AuthenticationPrincipal User user
  ) {
    logger.info("Received POST request on [/api/v1/bids/{}/reject]", bidId);
    return ResponseEntity.ok(bidMapper.toDto(bidService.rejectBid(bidId, user)));
  }

  /**
   * Cancel a bid.
   * Only the buyer of the bid can cancel a bid.
   *
   * @param bidId the id of the bid
   * @param user  the user who is canceling the bid
   * @return the bid response
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/bids/{bidId}/cancel")
  @Operation(summary = "Cancel a bid", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BidResponseDTO> cancelBid(
          @PathVariable UUID bidId,
          @AuthenticationPrincipal User user
  ) {
    logger.info("Received POST request on [/api/v1/bids/{}/cancel]", bidId);
    return ResponseEntity.ok(bidMapper.toDto(bidService.cancelBid(bidId, user)));
  }
}
