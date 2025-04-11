package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.CategoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting Category entities to DTOs and vice versa.
 * This class is responsible for transforming data between the model and the DTO layer.
 * It provides methods to convert a single Category entity to a CategoryResponseDTO
 * and a list of Category entities to a CategoryListResponseDTO.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @see CategoryResponseDTO
 * @see CategoryListResponseDTO
 * @since 1.0
 */
@Component
public class CategoryMapper {

  /**
   * Converts a Category entity to a CategoryResponseDTO.
   *
   * @param category The Category entity to convert.
   * @return The converted CategoryResponseDTO.
   * @throws IllegalArgumentException if the category argument is null.
   */
  public CategoryResponseDTO toDto(Category category) {
    if (category == null) {
      throw new IllegalArgumentException("category argument can not be null");
    }

    CategoryResponseDTO dto = new CategoryResponseDTO();
    dto.setId(category.getId());
    dto.setName(category.getName());

    return dto;
  }

  /**
   * Converts a list of Category entities to a CategoryListResponseDTO.
   *
   * @param categories The list of Category entities to convert.
   * @return The converted CategoryListResponseDTO.
   * @throws IllegalArgumentException if the category argument is null.
   */
  public CategoryListResponseDTO toDto(List<Category> categories) {
    if (categories == null) {
      throw new IllegalArgumentException("categories argument can not be null");
    }

    List<CategoryResponseDTO> categoryDtos = categories.stream()
            .map(this::toDto)
            .collect(Collectors.toList());

    return new CategoryListResponseDTO(categoryDtos);
  }
}