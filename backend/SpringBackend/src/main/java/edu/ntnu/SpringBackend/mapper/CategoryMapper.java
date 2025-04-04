package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.CategoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    /*public Category toEntity(CategoryCreationRequestDTO dto) { TODO: remove
        if (dto == null) {
            throw new IllegalArgumentException("dto argument can not be null");
        }

        Category category = new Category();
        category.setName(dto.getName());
        // Set other fields if necessary

        return category;
    }*/

    public CategoryResponseDTO toDto(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category argument can not be null");
        }

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

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