package edu.ntnu.SpringBackend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Bid requests.
 * <p>
 * This class is used to transfer bid data between the client and server.
 * It contains the price of the bid, which must be provided and must be 0 or greater.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidRequestDTO {
    @NotNull(message = "Price must be provided")
    @Min(value = 0, message = "Bid price must be 0 (free) or greater")
    private Double price;
}
