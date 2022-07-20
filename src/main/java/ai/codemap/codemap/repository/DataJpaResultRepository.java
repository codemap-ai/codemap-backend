package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaResultRepository extends JpaRepository<Result, Integer>, ResultRepository {
    @Override
    List<Result> findByPid(int pid);

    @Override
    List<Result> findByUid(int uid);
}
