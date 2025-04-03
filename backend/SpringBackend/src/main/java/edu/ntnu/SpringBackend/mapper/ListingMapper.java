package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.ListingCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.ListingListResponseDTO;
import edu.ntnu.SpringBackend.dto.ListingResponseDTO;
import edu.ntnu.SpringBackend.model.Listing;
import edu.ntnu.SpringBackend.model.enums.ListingStatus;
import edu.ntnu.SpringBackend.repository.CategoryRepository;
import edu.ntnu.SpringBackend.service.CategoryService;
import edu.ntnu.SpringBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ListingMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    public ListingResponseDTO toDto(Listing listing) {
        if (listing == null) {
            throw new IllegalArgumentException("listing argument can not be null");
        }

        ListingResponseDTO dto = new ListingResponseDTO();
        dto.setId(listing.getId());
        dto.setSellerId(listing.getSeller().getId());
        dto.setTitle(listing.getTitle());
        dto.setDescription(listing.getDescription());
        dto.setCategoryName(listing.getCategory().getName());
        dto.setListingStatus(listing.getStatus().name());
        dto.setPrice(listing.getPrice());
        dto.setLatitude(listing.getLatitude());
        dto.setLongitude(listing.getLongitude());
        dto.setCreatedAt(listing.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        dto.setLastEditedAt(listing.getLastEditedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        //dto.setChatId(UUID.fromString("NotYetImplemented")); // TODO: implement when chat system is done

        return dto;
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

    public Listing toEntity(ListingResponseDTO dto) {
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
        //listing.setChat(); TODO: implement when chat is implemented

        return listing;
    }

    public Listing toEntity(ListingCreationRequestDTO dto){
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
        //listing.setChat(); TODO: implement when chat is implemented

        return listing;

    }
}