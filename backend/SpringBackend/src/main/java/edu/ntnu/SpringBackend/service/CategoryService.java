package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing categories.
 * This class provides methods to create, delete, and retrieve categories.
 * It also includes functionality to match categories based on search history.
 * <p>
 * This class is responsible for the business logic related to categories.
 * It interacts with the CategoryRepository to perform CRUD operations
 * and other category-related tasks.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    /**
     * Fetches all categories from the database.
     *
     * @return A list of all categories.
     */
    public List<Category> getAll() {
        logger.info("> Fetching all categories");
        return new ArrayList<>(categoryRepository.findAll());
    }

    /**
     * Fetches a category by its name.
     *
     * @param name The name of the category to fetch.
     * @return The category with the specified name.
     * @throws NoSuchElementException if the category with the specified name does not exist.
     */
    public Category getByName(String name) {
        logger.info("> Fetching category by name: {}", name);
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NoSuchElementException("Category with name " + name + " not found"));
    }

    /**
     * Fetches a category by its ID.
     *
     * @param id The ID of the category to fetch.
     * @return The category with the specified ID.
     * @throws NoSuchElementException if the category with the specified ID does not exist.
     */
    public Category getById(UUID id) {
        logger.info("> Fetching category by ID: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with ID " + id + " not found"));
    }

    /**
     * Adds a new category to the database.
     *
     * @param requestDTO The request DTO containing category creation details.
     * @param user       The authenticated user making the request.
     * @return The created category.
     * @throws AccessDeniedException if the user does not have the ADMIN role.
     * @throws IllegalArgumentException if a category with the same name already exists.
     */
    @Transactional
    public Category add(CategoryCreationRequestDTO requestDTO, @AuthenticationPrincipal User user) {
        logger.info("> Adding new category: {}", requestDTO.getName());
        if (user == null || !user.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("User is not authorized to create categories");
        }
        if (categoryRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new IllegalArgumentException("Category with name " + requestDTO.getName().toLowerCase() + " already exists");
        }
        Category category = Category.builder()
                .name(requestDTO.getName())
                .build();

        return categoryRepository.save(category);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id   The ID of the category to delete.
     * @param user The authenticated user making the request.
     * @throws AccessDeniedException if the user does not have the ADMIN role.
     * @throws NoSuchElementException if the category with the specified ID does not exist.
     */
    @Transactional
    public void delete(UUID id, @AuthenticationPrincipal User user) {
        logger.info("> Deleting category with ID: {}", id);
        if (user == null || !user.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("User is not authorized to delete categories");
        }

        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Category with ID " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }

    /**
     * Finds categories based on a list of past search queries.
     *
     * @param searchHistoryList List of past search history objects
     * @return List of matched Category objects (alphabetically sorted by name, no duplicates)
     */
    public List<Category> findBySearchHistory(List<SearchHistory> searchHistoryList) {
        logger.info("> Finding categories by search history");
        if (searchHistoryList == null || searchHistoryList.isEmpty()) {
            logger.warn("> Search history list is null or empty, returning all categories");
            return categoryRepository.findAll();
        }

        List<String> searchQueries = searchHistoryList.stream()
                .map(SearchHistory::getSearchQuery)
                .collect(Collectors.toList());

        List<Category> allCategories = categoryRepository.findAll();

        return matchCategories(searchQueries, allCategories);
    }

    /**
     * Matches search queries to a list of Category objects by checking if the category name
     * is contained in the query string (case-insensitive).
     *
     * @param searchQueries List of past search query strings
     * @param categories List of Category objects
     * @return List of matched Category objects (alphabetically sorted by name, no duplicates)
     */
    public List<Category> matchCategories(List<String> searchQueries, List<Category> categories) {
        List<Category> matched = new ArrayList<>();

        for (String query : searchQueries) {
            String queryLower = query.toLowerCase();

            for (Category category : categories) {
                String categoryName = category.getName().toLowerCase();

                if (queryLower.contains(categoryName) && !matched.contains(category)) {
                    matched.add(category);
                }
            }
        }

        matched.sort(Comparator.comparing(Category::getName));
        return matched;
    }
}
