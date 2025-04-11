package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.BidResponseDTO;
import edu.ntnu.SpringBackend.model.Bid;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting Bid entities to BidResponseDTOs.
 * <p>
 * This class is responsible for mapping the fields of a Bid entity to a BidResponseDTO.
 * It uses the Builder pattern to create instances of BidResponseDTO.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Component
public class BidMapper {

  /**
   * Converts a Bid entity to a BidResponseDTO.
   *
   * @param bid The Bid entity to convert.
   * @return A BidResponseDTO containing the mapped fields from the Bid entity.
   */
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
