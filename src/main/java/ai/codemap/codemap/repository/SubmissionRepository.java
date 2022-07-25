package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Submission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmissionRepository {
    Submission findById(int submissionId);
    List<Submission> findAll();
    Submission save(Submission submission);
    List<Submission> findByContestId(int contestId);

    List<Submission> findByUserId(int userId);
    List<Submission> findByProblemId(int problemId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Submission s SET s.problemId = :problemId WHERE s.submissionId = :submissionId")
    int updateProblemId(int problemId, int submissionId);

}
