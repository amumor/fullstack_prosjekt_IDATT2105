package edu.ntnu.SpringBackend.model;

import edu.ntnu.SpringBackend.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * User is an entity class representing a user in the system.
 * It implements the UserDetails interface from Spring Security
 * to provide user authentication and authorization functionality.
 * <p>
 * The class contains fields for user details such as ID, password, email,
 * phone number, first name, last name, role, and relationships with listings,
 * bookmarks, and search history.
 * <p>
 * The class is annotated with JPA annotations to map it to a database table.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

  /**
   * The unique identifier for the user.
   * This field is automatically generated and cannot be null.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * The password of the user.
   * This field is required and cannot be null.
   */
  @Column(nullable = false)
  private String password;

  /**
   * The email address of the user.
   * This field is required and cannot be null.
   */
  @Column(unique = true, nullable = false)
  private String email;

  /**
   * The phone number of the user.
   * This field is required and cannot be null.
   */
  @Column(unique = true, nullable = false)
  private String phoneNumber;

  /**
   * The first name of the user.
   * This field is required and cannot be null.
   */
  @Column(nullable = false)
  private String firstName;

  /**
   * The last name of the user.
   * This field is required and cannot be null.
   */
  @Column(nullable = false)
  private String lastName;

  /**
   * The role of the user.
   * This field is required and cannot be null.
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  /**
   * The list of listings created by the user.
   * This field is mapped to the "seller" field in the Listing entity.
   * It is a one-to-many relationship, where one user can have multiple listings.
   */
  @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Listing> listings = new ArrayList<>();

  /**
   * The list of bookmarks created by the user.
   * This field is mapped to the "user" field in the Bookmark entity.
   * It is a one-to-many relationship, where one user can have multiple bookmarks.
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Bookmark> bookmarks = new ArrayList<>();

  /**
   * The list of search history entries created by the user.
   * This field is mapped to the "user" field in the SearchHistory entity.
   * It is a one-to-many relationship, where one user can have multiple search history entries.
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<SearchHistory> searchHistoryList = new ArrayList<>();


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  /**
   * Indicates whether the user's account is expired.
   * This method is part of the UserDetails interface.
   *
   * @return true if the account is expired, false otherwise.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", firstname='" + firstName + '\'' +
            ", lastname='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", role=" + role +
            '}';
  }
}
