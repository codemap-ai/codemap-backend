package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaProblemSetRepository extends JpaRepository<ProblemSet, Integer>, ProblemSetRepository {
}
