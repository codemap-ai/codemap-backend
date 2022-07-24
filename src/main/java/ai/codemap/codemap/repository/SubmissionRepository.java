package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Submission;

import java.util.List;

public interface SubmissionRepository {
    Submission findById(int submissionId);
    List<Submission> findAll();
    Submission save(Submission submission);
    List<Submission> findByContestId(int contestId);
    List<Submission> findByUserId(int userId);
    List<Submission> findByProblemId(int problemId);

}
