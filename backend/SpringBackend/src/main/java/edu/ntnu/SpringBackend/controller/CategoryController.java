package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.CategoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.mapper.CategoryMapper;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> create(
            @AuthenticationPrincipal User user,
            @RequestBody CategoryCreationRequestDTO request
    ) {
        logger.info("POST Request received on [/api/v1/category/create]");
        Category category = Category.builder()
                .name(request.getName())
                .build();
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.add(category, user)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("DELETE request received on [/api/v1/category/delete/{}]", id);
        categoryService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDTO> getByName(
            @PathVariable String name
    ) {
        logger.info("GET request received on [/api/v1/category/name/{}]", name);
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getByName(name)));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET request received on [/api/v1/category/id/{}]", id);
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<CategoryListResponseDTO> getAll() {
        logger.info("GET request received on [/api/v1/category/all]");
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getAll()));
    }
}