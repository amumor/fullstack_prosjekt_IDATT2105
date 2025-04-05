package edu.ntnu.SpringBackend.repository;

import edu.ntnu.SpringBackend.model.SearchHistory;
import edu.ntnu.SpringBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, UUID> {
    List<SearchHistory> findByUserIdOrderBySearchedAtDesc(UUID userId);

    @Modifying
    @Query(value = """
            DELETE FROM search_history
            WHERE id IN (
                SELECT id FROM (
                    SELECT id,
                           ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY searched_at DESC) AS rn
                    FROM search_history
                    WHERE user_id = :userId
                ) sub
                WHERE rn > 15
            )
            """, nativeQuery = true)
    void deleteOldSearches(@Param("userId") Long userId);

    UUID user(User user);
}
