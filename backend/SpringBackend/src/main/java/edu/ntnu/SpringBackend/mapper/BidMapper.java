package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.model.Bid;
import org.springframework.stereotype.Component;

@Component
public class BidMapper {
  public BidResponseDTO toDto(Bid bid) {
    return BidResponseDTO.builder()
        .id(bid.getId())
        .chatId(bid.getChat().getId())
        .price(bid.getPrice())
        .status(bid.getStatus())
        .timestamp(bid.getTimestamp())
        .build();
  }

}
