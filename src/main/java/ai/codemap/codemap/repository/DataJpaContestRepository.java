package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaContestRepository extends JpaRepository<Contest, Integer>, ContestRepository {
}
