package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaProblemRepository extends JpaRepository<Problem, Integer>, ProblemRepository {
}
