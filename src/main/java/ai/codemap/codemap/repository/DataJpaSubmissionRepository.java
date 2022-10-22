package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaSubmissionRepository extends JpaRepository<Submission, Long>, SubmissionRepository {
    @Override
    List<Submission> findByProblemId(Long problem_id);

    @Override
    List<Submission> findByContestId(Long contest_id);

    @Override
    List<Submission> findByUserId(Long user_id);

}
