package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.LoadCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataJpaLoadCodeRepository extends JpaRepository<LoadCode, Long>, LoadCodeRepository{
    @Query("select lc from LoadCode lc where lc.contestId = :contestId and lc.problemId =:problemId")
    LoadCode findByContestIdAndProblemId(@Param("contestId") int contestId, @Param("problemId")int problemId);
}
