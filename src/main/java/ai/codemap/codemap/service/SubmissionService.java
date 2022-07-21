package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.repository.SubmissionRepository;

import java.util.List;

public class SubmissionService {
    private SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public Submission getOne(int submission_id) {
        return submissionRepository.findById(submission_id);
    }

    public List<Submission> getByUserId(int userId) {
        return submissionRepository.findByUserId(userId);
    }

    public List<Submission> getByProblemId(int problemId) {
        return submissionRepository.findByProblemId(problemId);
    }

    public List<Submission> getByTestId(int testId) {
        return submissionRepository.findByTestId(testId);
    }

    public List<Submission> getAll() {
        return submissionRepository.findAll();
    }

}
