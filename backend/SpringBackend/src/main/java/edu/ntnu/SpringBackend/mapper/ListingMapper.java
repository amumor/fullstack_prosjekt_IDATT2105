package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.ListingListResponseDTO;
import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.ListingImage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting Listing entities to DTOs and vice versa.
 * This class is responsible for transforming the data between the entity and DTO layers.
 * <p>
 * It contains methods to convert a single Listing entity to a ListingResponseDTO,
 * as well as a method to convert a list of Listing entities to a ListingListResponseDTO.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Component
public class ListingMapper {

  /**
   * Converts a Listing entity to a ListingResponseDTO.
   *
   * @param listing the Listing entity to convert
   * @return the converted ListingResponseDTO
   * @throws IllegalArgumentException if the listing argument is null
   */
  public ListingResponseDTO toDto(Listing listing) {
    if (listing == null) {
      throw new IllegalArgumentException("listing argument can not be null");
    }

    return ListingResponseDTO.builder()
            .id(listing.getId())
            .sellerFirstName(listing.getSeller().getFirstName())
            .sellerLastName(listing.getSeller().getLastName())
            .title(listing.getTitle())
            .description(listing.getDescription())
            .categoryName(listing.getCategory().getName())
            .listingStatus(listing.getStatus().name())
            .price(listing.getPrice())
            .latitude(listing.getLatitude())
            .longitude(listing.getLongitude())
            .createdAt(listing.getCreatedAt())
            .lastEditedAt(listing.getLastEditedAt())
            .imageUrls(listing.getImages().stream()
                    .map(ListingImage::getImageUrl)
                    .toList())
            .build();
  }

  /**
   * Converts a list of Listing entities to a ListingListResponseDTO.
   *
   * @param listings the list of Listing entities to convert
   * @return the converted ListingListResponseDTO
   */
  public ListingListResponseDTO toDto(List<Listing> listings) {
    List<ListingResponseDTO> dtoList = new ArrayList<>();
    for (Listing listing : listings) {
      ListingResponseDTO dto = this.toDto(listing);
      dtoList.add(dto);
    }
    ListingListResponseDTO listingListResponseDTO = new ListingListResponseDTO();
    listingListResponseDTO.setListings(dtoList);
    return listingListResponseDTO;
  }
}