package ai.codemap.codemap.repository;


import ai.codemap.codemap.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
