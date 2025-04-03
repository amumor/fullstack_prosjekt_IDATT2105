package edu.ntnu.SpringBackend.controller;

import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.dto.CategoryResponseDTO;
import edu.ntnu.SpringBackend.mapper.CategoryMapper;
import edu.ntnu.SpringBackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryMapper categoryMapper;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryCreationRequestDTO request) {
        logger.info("POST Request received on [/api/v1/category/create]");
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.add(categoryMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        logger.info("DELETE Request received on [/api/v1/category/delete/{}]", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDTO> getByName(@PathVariable String name) {
        logger.info("GET Request received on [/api/v1/category/name/{}]", name);
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getByName(name)));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID id) {
        logger.info("GET Request received on [/api/v1/category/id/{}]", id);
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getById(id)));
    }
}