package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.mapper.UserMapper;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        logger.info("Fetching all categories...");
        return new ArrayList<>(categoryRepository.findAll());
    }

    public Category getByName(String name) {
        logger.info("Fetching category by name: {}", name);
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Category with name " + name + " not found"));
    }

    @Transactional
    public Category addCategory(Category category) {
        logger.info("Adding new category: {}", category.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategoryById(UUID id) {
        logger.info("Deleting category with ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Category with ID " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }
}
