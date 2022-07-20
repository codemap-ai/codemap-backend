package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaAlgorithmRepository extends JpaRepository<Algorithm, Integer>, AlgorithmRepository {

}
