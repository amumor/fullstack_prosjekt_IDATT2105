package edu.ntnu.SpringBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryResponseDTO {
    private String searchQuery;
    private LocalDateTime searchedAt;
}
