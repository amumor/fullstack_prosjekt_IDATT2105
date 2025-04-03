package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.mapper.UserMapper;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    public Category getById(UUID id) {
        logger.info("Fetching category by ID: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with ID " + id + " not found"));
    }

    @Transactional
    public Category add(Category category) {
        logger.info("Adding new category: {}", category.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Deleting category with ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Category with ID " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }

    public List<Category> findBySearchHistory(List<SearchHistory> searchHistoryList) {
        logger.info("Finding categories by search history...");
        if (searchHistoryList == null || searchHistoryList.isEmpty()) {
            return new ArrayList<>();
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
    public static List<Category> matchCategories(List<String> searchQueries, List<Category> categories) {
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
