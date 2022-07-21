package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public class ProblemService {

    private final ProblemRepository problemRepository;
    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public Problem getOne(int problem_id) {
        return problemRepository.findById(problem_id);
    }

    public List<Problem> getAll() {
        return problemRepository.findAll();
    }
}
