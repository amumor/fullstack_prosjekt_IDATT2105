package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.ListingListResponseDTO;
import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.model.Listing;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListingMapper {
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
                .createdAt(listing.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .lastEditedAt(listing.getLastEditedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

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

    /*public Listing toEntity(ListingResponseDTO dto) { TODO: remove / fix
        if (dto == null) {
            throw new IllegalArgumentException("dto argument can not be null");
        }

        Listing listing = new Listing();
        listing.setId(dto.getId());
        listing.setSeller(userService.getUserById(dto.getSellerId()));
        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setCategory(categoryService.getByName(dto.getCategoryName()));
        listing.setStatus(ListingStatus.valueOf(dto.getListingStatus()));
        listing.setPrice(dto.getPrice());
        listing.setLatitude(dto.getLatitude());
        listing.setLongitude(dto.getLongitude());
        listing.setCreatedAt(LocalDateTime.parse(dto.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME));
        listing.setLastEditedAt(LocalDateTime.parse(dto.getLastEditedAt(), DateTimeFormatter.ISO_DATE_TIME));

        return listing;
    }*/

    /*public Listing toEntity(ListingCreationRequestDTO dto){ TODO: remove / fix
        if (dto == null) {
            throw new IllegalArgumentException("dto argument can not be null");
        }
        Listing listing = new Listing();
        listing.setSeller(userService.getUserById(dto.getOwnerId()));
        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setCategory(categoryService.getByName(dto.getCategoryName()));
        listing.setStatus(ListingStatus.valueOf(dto.getListingStatus()));
        listing.setPrice(dto.getPrice());
        listing.setLatitude(dto.getLatitude());
        listing.setLongitude(dto.getLongitude());
        listing.setCreatedAt(LocalDateTime.parse(dto.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME));
        listing.setLastEditedAt(LocalDateTime.parse(dto.getLastEditedAt(), DateTimeFormatter.ISO_DATE_TIME));
        //listing.setChat();

        return listing;

    }*/
}