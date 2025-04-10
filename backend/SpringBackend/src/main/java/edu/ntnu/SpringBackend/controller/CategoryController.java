package edu.ntnu.SpringBackend.controller;


import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.CategoryListResponseDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.mapper.CategoryMapper;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing categories.
 * Only users with the ADMIN role can access the endpoints in this controller.
 * This class handles HTTP requests related to category operations such as creating,
 * deleting, and retrieving categories.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryMapper categoryMapper;

    /**
     * Creates a new category.
     * This endpoint is secured and requires the user to have the ADMIN role.
     *
     * @param user    The authenticated user making the request.
     * @param request The request body containing category creation details.
     * @return A response entity containing the created category details.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "Create a new category", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CategoryResponseDTO> create(
            @AuthenticationPrincipal User user,
            @RequestBody CategoryCreationRequestDTO request
    ) {
        logger.info("POST Request received on [/api/v1/category/create]");
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.add(request, user)));
    }

    /**
     * Deletes a category by its ID.
     * This endpoint is secured and requires the user to have the ADMIN role.
     *
     * @param user The authenticated user making the request.
     * @param id   The ID of the category to be deleted.
     * @return A response entity with no content if the deletion was successful.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a category", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id
    ) {
        logger.info("DELETE request received on [/api/v1/category/delete/{}]", id);
        categoryService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a category by its name.
     * This endpoint is available to all users.
     *
     * @param name The name of the category to be retrieved.
     * @return A response entity containing the category details.
     */
    @GetMapping("/name/{name}")
    @Operation(summary = "Get a category by name")
    public ResponseEntity<CategoryResponseDTO> getByName(
            @PathVariable String name
    ) {
        logger.info("GET request received on [/api/v1/category/name/{}]", name);
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getByName(name)));
    }

    /**
     * Retrieves a category by its ID.
     * This endpoint is available to all users.
     *
     * @param id The ID of the category to be retrieved.
     * @return A response entity containing the category details.
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Get a category by ID")
    public ResponseEntity<CategoryResponseDTO> getById(
            @PathVariable UUID id
    ) {
        logger.info("GET request received on [/api/v1/category/id/{}]", id);
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getById(id)));
    }

    /**
     * Retrieves all categories.
     * This endpoint is available to all users.
     *
     * @return A response entity containing a list of all categories.
     */
    @GetMapping("/all")
    @Operation(summary = "Get all categories")
    public ResponseEntity<CategoryListResponseDTO> getAll() {
        logger.info("GET request received on [/api/v1/category/all]");
        List<Category> categories = categoryService.getAll();
        logger.info("Categories found: {}", categories.toString());
        CategoryListResponseDTO categoryListResponseDTO = categoryMapper.toDto(categories);
        return ResponseEntity.ok(categoryListResponseDTO);
    }
}