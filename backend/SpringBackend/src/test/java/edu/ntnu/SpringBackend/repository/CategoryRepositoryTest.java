package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  public void testSaveAndFindById() {
    Category category = Category.builder()
            .name("TestRepo")
            .build();
    Category saved = categoryRepository.save(category);

    Optional<Category> found = categoryRepository.findById(saved.getId());
    assertTrue(found.isPresent());
    assertEquals("TestRepo", found.get().getName());
  }

  @Test
  public void testFindByNameIgnoreCase() {
    Category category = Category.builder()
            .name("UniqueName")
            .build();
    categoryRepository.save(category);

    Optional<Category> found = categoryRepository.findByNameIgnoreCase("uniquename");
    assertTrue(found.isPresent());
    assertEquals("UniqueName", found.get().getName());
  }

  @Test
  public void testExistsByNameIgnoreCase() {
    Category category = Category.builder()
            .name("ExistsName")
            .build();
    categoryRepository.save(category);

    boolean exists = categoryRepository.existsByNameIgnoreCase("existsname");
    assertTrue(exists);
  }
}
