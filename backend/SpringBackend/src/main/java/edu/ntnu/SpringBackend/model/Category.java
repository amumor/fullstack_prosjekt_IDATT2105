package edu.ntnu.SpringBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a category.
 * This class is used to represent a category in the database.
 * It contains the necessary fields, an id and a name, to represent a category.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    /**
     * The unique identifier of the category.
     * This field is automatically generated and cannot be null.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the category.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The list of listings associated with this category.
     * This field is mapped by the "category" field in the Listing class.
     * It is automatically managed by JPA and will be removed if the category is deleted.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Listing> listings = new ArrayList<>();
}
