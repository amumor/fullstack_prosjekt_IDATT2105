package edu.ntnu.SpringBackend.mapper;

import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryCreationRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto argument can not be null");
        }

        Category category = new Category();
        category.setName(dto.getName());
        // Set other fields if necessary

        return category;
    }

    public CategoryResponseDTO toDto(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category argument can not be null");
        }

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        // Set other fields if necessary

        return dto;
    }
}