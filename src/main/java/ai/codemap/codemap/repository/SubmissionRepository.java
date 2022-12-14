package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Submission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository {
    Optional<Submission> findById(Long submissionId);
    List<Submission> findAll();
    Submission save(Submission submission);
    List<Submission> findByContestId(Long contestId);

    List<Submission> findByUserId(Long userId);
    List<Submission> findByProblemId(Long problemId);
    List<Submission> findByUserIdAndSuccess(Long userId);
    List<Submission> findByUserIdAndContestId(Long userId, Long contestId);

}
