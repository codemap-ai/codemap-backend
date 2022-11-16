package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataJpaSubmissionRepository extends JpaRepository<Submission, Long>, SubmissionRepository {
    @Override
    List<Submission> findByProblemId(Long problem_id);

    @Override
    List<Submission> findByContestId(Long contest_id);

    @Override
    List<Submission> findByUserId(Long user_id);
    @Query("select sb from Submission sb where sb.result='ACCEPTED' and sb.userId = :userId")
    List<Submission> findByUserIdAndSuccess(Long userId);
}
