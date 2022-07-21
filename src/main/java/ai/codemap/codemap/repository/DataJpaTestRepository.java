package ai.codemap.codemap.repository;
import ai.codemap.codemap.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface DataJpaTestRepository extends JpaRepository<Test, Integer>, TestRepository {
}
