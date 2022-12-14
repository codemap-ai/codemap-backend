package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByUsername(String username);

   @Query("select u.userId from User u where u.username = :username")
   Long getIdByUsername(@Param("username") String username);
   User findByUserId(Long userId);
   User findByUsername(String username);

   @Query("select u from User u where u.socialId = :social_id")
   User findBySocialId(@Param("social_id") String social_id);
}
