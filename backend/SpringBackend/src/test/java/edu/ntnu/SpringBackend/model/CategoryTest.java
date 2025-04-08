package edu.ntnu.SpringBackend.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

  @Test
  public void testBuilderAndGetters() {
    UUID id = UUID.randomUUID();
    Category category = Category.builder()
            .id(id)
            .name("Books")
            .build();

    assertEquals(id, category.getId());
    assertEquals("Books", category.getName());
    assertNotNull(category.getListings());
    assertTrue(category.getListings().isEmpty());
  }

  @Test
  public void testSettersAndEquals() {
    Category category1 = new Category();
    category1.setId(UUID.randomUUID());
    category1.setName("Movies");

    Category category2 = new Category();
    category2.setId(category1.getId());
    category2.setName("Movies");

    // Using Lombok-generated equals method
    assertEquals(category1, category2);
  }
}
