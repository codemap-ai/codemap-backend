package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public Problem getOne(Long problem_id) {
        return problemRepository.findById(problem_id);
    }

    public List<Problem> getAll() {
        return problemRepository.findAll();
    }

    public Long createProblem() { // returns problem id
        Problem problem = new Problem();
        problem.setTitle("New Problem");
        problem.setBody("");
        problem.setMemoryLimit(512 * 1024L);
        problem.setTimeLimit(1.0);
        problemRepository.save(problem);
        return problem.getProblemId();
    }
}
