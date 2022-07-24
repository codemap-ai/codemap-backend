package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataJpaSubmissionRepository extends JpaRepository<Submission, Integer>, SubmissionRepository {
    @Override
    List<Submission> findByProblemId(int problem_id);

    @Override
    List<Submission> findByContestId(int contest_id);

    @Override
    List<Submission> findByUserId(int user_id);

}
