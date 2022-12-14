package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.repository.SubmissionRepository;

import java.util.List;

public class SubmissionService {
    private SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public Submission getOne(Long submission_id) {
        return submissionRepository.findById(submission_id).get();
    }

    public Submission addSubmission(Submission submission) {

        submissionRepository.save(submission);
        return submission;
    }

    public List<Submission> getByUserId(Long userId) {
        return submissionRepository.findByUserId(userId);
    }

    public List<Submission> getByProblemId(Long problemId) {
        return submissionRepository.findByProblemId(problemId);
    }


    public List<Submission> getByContestId(Long contestId) {
        return submissionRepository.findByContestId(contestId);
    }

    public List<Submission> getAll() {
        return submissionRepository.findAll();
    }

    public List<Submission> getByUserIdAndSuccess(Long userId) {return submissionRepository.findByUserIdAndSuccess(userId);}
}
