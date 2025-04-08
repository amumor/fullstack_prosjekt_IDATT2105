package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.CategoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.model.Category;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMapperTest {

  private final CategoryMapper mapper = new CategoryMapper();

  @Test
  public void testToDto_SingleCategory() {
    UUID id = UUID.randomUUID();
    Category category = Category.builder()
            .id(id)
            .name("Garden")
            .build();
    CategoryResponseDTO dto = mapper.toDto(category);
    assertNotNull(dto);
    assertEquals(id, dto.getId());
    assertEquals("Garden", dto.getName());
  }

  @Test
  public void testToDto_NullCategory() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      mapper.toDto((Category) null);
    });
    assertEquals("category argument can not be null", exception.getMessage());
  }

  @Test
  public void testToDto_EmptyList() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      mapper.toDto((List<Category>) null);
    });
    assertEquals("categories argument can not be null", exception.getMessage());
  }

  @Test
  public void testToDto_ListOfCategories() {
    Category category1 = Category.builder().id(UUID.randomUUID()).name("Alpha").build();
    Category category2 = Category.builder().id(UUID.randomUUID()).name("Beta").build();
    CategoryListResponseDTO listDTO = mapper.toDto(Arrays.asList(category1, category2));
    assertNotNull(listDTO);
    assertEquals(2, listDTO.getCategories().size());
    // Mapper in the controller sorts by name if required; here we verify names are mapped correctly.
    assertEquals("Alpha", listDTO.getCategories().get(0).getName());
    assertEquals("Beta", listDTO.getCategories().get(1).getName());
  }

  @Test
  public void testToDto_NullList() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      mapper.toDto((java.util.List<Category>) null);
    });
    assertEquals("categories argument can not be null", exception.getMessage());
  }
}
