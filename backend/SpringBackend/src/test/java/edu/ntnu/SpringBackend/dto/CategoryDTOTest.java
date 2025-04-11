package edu.ntnu.SpringBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDTOTest {

  @Test
  public void testCategoryCreationRequestDTO() {
    CategoryCreationRequestDTO dto = CategoryCreationRequestDTO.builder()
            .name("Sports")
            .build();
    assertEquals("Sports", dto.getName());
  }

  @Test
  public void testCategoryResponseDTO() {
    // Use a random UUID for testing
    java.util.UUID id = java.util.UUID.randomUUID();
    CategoryResponseDTO dto = CategoryResponseDTO.builder()
            .id(id)
            .name("Music")
            .build();
    assertEquals(id, dto.getId());
    assertEquals("Music", dto.getName());
  }

  @Test
  public void testCategoryListResponseDTO() {
    CategoryResponseDTO dto1 = CategoryResponseDTO.builder()
            .id(java.util.UUID.randomUUID())
            .name("Tech")
            .build();
    CategoryResponseDTO dto2 = CategoryResponseDTO.builder()
            .id(java.util.UUID.randomUUID())
            .name("Health")
            .build();
    CategoryListResponseDTO listDTO = CategoryListResponseDTO.builder()
            .categories(Arrays.asList(dto1, dto2))
            .build();

    assertNotNull(listDTO.getCategories());
    assertEquals(2, listDTO.getCategories().size());
  }
}
