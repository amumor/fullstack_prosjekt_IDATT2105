package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.dto.CategoryCreationRequestDTO;
import edu.ntnu.SpringBackend.model.Category;
import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import edu.ntnu.SpringBackend.model.enums.Role;
import edu.ntnu.SpringBackend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  private Category category;
  private User adminUser;
  private User normalUser;

  @BeforeEach
  void setup() {
    category = Category.builder()
            .id(UUID.randomUUID())
            .name("Electronics")
            .build();

    adminUser = new User();
    adminUser.setRole(Role.ROLE_ADMIN);

    normalUser = new User();
    normalUser.setRole(Role.ROLE_USER);
  }

  @Test
  void testGetAll() {
    when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
    List<Category> result = categoryService.getAll();
    assertEquals(1, result.size());
    verify(categoryRepository).findAll();
  }

  @Test
  void testGetByName_Success() {
    when(categoryRepository.findByNameIgnoreCase("Electronics")).thenReturn(Optional.of(category));
    Category result = categoryService.getByName("Electronics");
    assertEquals(category, result);
    verify(categoryRepository).findByNameIgnoreCase("Electronics");
  }

  @Test
  void testGetByName_NotFound() {
    when(categoryRepository.findByNameIgnoreCase("Unknown")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> categoryService.getByName("Unknown"));
  }

  @Test
  void testGetById_Success() {
    UUID categoryId = UUID.randomUUID();
    Category category = Category.builder().id(categoryId).name("Electronics").build();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

    Category result = categoryService.getById(categoryId);

    assertNotNull(result);
    assertEquals(categoryId, result.getId());
    assertEquals("Electronics", result.getName());
    verify(categoryRepository, times(1)).findById(categoryId);
  }

  @Test
  void testGetById_NotFound() {
    UUID categoryId = UUID.randomUUID();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> categoryService.getById(categoryId));
    verify(categoryRepository, times(1)).findById(categoryId);
  }

  @Test
  void testAdd_Success() {
    CategoryCreationRequestDTO dto = new CategoryCreationRequestDTO("NewCategory");
    when(categoryRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
    when(categoryRepository.save(any(Category.class))).thenReturn(category);
    Category created = categoryService.add(dto, adminUser);
    assertNotNull(created);
    verify(categoryRepository).save(any(Category.class));
  }

  @Test
  void testAdd_Exists() {
    CategoryCreationRequestDTO dto = new CategoryCreationRequestDTO("Electronics");
    when(categoryRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);
    assertThrows(IllegalArgumentException.class, () -> categoryService.add(dto, adminUser));
  }

  @Test
  void testAdd_Unauthorized() {
    CategoryCreationRequestDTO dto = new CategoryCreationRequestDTO("NewCategory");
    assertThrows(AccessDeniedException.class, () -> categoryService.add(dto, normalUser));
  }

  @Test
  void testDelete_Success() {
    UUID id = UUID.randomUUID();
    when(categoryRepository.existsById(id)).thenReturn(true);
    doNothing().when(categoryRepository).deleteById(id);
    assertDoesNotThrow(() -> categoryService.delete(id, adminUser));
  }

  @Test
  void testDelete_NotFound() {
    UUID id = UUID.randomUUID();
    when(categoryRepository.existsById(id)).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> categoryService.delete(id, adminUser));
  }

  @Test
  void testDelete_Unauthorized() {
    UUID id = UUID.randomUUID();
    assertThrows(AccessDeniedException.class, () -> categoryService.delete(id, normalUser));
  }

  @Test
  void testFindBySearchHistory_Success() {
    List<SearchHistory> searchHistoryList = List.of(
            SearchHistory.builder().searchQuery("Electronics").build(),
            SearchHistory.builder().searchQuery("Home Appliances").build()
    );

    List<Category> categories = List.of(
            Category.builder().name("Electronics").build(),
            Category.builder().name("Furniture").build(),
            Category.builder().name("Home Appliances").build()
    );

    when(categoryRepository.findAll()).thenReturn(categories);

    List<Category> result = categoryService.findBySearchHistory(searchHistoryList);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.stream().anyMatch(c -> c.getName().equals("Electronics")));
    assertTrue(result.stream().anyMatch(c -> c.getName().equals("Home Appliances")));
    verify(categoryRepository, times(1)).findAll();
  }

  @Test
  void testFindBySearchHistory_EmptySearchHistory() {
    List<SearchHistory> searchHistoryList = Collections.emptyList();

    List<Category> result = categoryService.findBySearchHistory(searchHistoryList);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testFindBySearchHistory_EmptyHistory() {
    List<Category> matched = categoryService.findBySearchHistory(Collections.emptyList());
    assertTrue(matched.isEmpty());
  }

  @Test
  void testMatchCategories_Success() {
    List<String> searchQueries = List.of("Electronics", "Home Appliances");
    List<Category> categories = List.of(
            Category.builder().name("Electronics").build(),
            Category.builder().name("Furniture").build(),
            Category.builder().name("Home Appliances").build()
    );

    List<Category> result = categoryService.matchCategories(searchQueries, categories);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Electronics", result.get(0).getName());
    assertEquals("Home Appliances", result.get(1).getName());
  }

  @Test
  void testMatchCategories_NoMatches() {
    List<String> searchQueries = List.of("Books", "Toys");
    List<Category> categories = List.of(
            Category.builder().name("Electronics").build(),
            Category.builder().name("Furniture").build()
    );

    List<Category> result = categoryService.matchCategories(searchQueries, categories);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}
