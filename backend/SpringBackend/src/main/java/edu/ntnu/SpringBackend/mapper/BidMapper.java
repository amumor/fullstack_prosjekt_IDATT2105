package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.model.Bid;

public class BidMapper {
  public BidResponseDTO toDto(Bid bid) {
    return BidResponseDTO.builder()
        .id(bid.getId())
        .chatId(bid.getChat().getId())
        .buyerId(bid.getBuyer().getId())
        .price(bid.getPrice())
        .status(bid.getStatus())
        .timestamp(bid.getTimestamp())
        .build();
  }

}
