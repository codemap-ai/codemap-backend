package ai.codemap.codemap.service;

import ai.codemap.codemap.form.ProblemForm;
import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public Problem getOne(Long problem_id) {
        return problemRepository.findById(problem_id);
    }

    public List<Problem> getAll() {
        return problemRepository.findAll();
    }

    public Long createProblem() { // returns problem id
        Problem problem = new Problem();
        problem.setTitle("New Problem");
        problem.setLegend("");
        problem.setInputFormat("");
        problem.setOutputFormat("");
        problem.setMemoryLimit(512L); // default memory limit = 512MiB
        problem.setTimeLimit(1.0); // default time limit = 1 second
        problemRepository.save(problem);
        return problem.getProblemId();
    }

    public void updateProblem(ProblemForm problemForm) {
        Problem problem = problemRepository.findById(problemForm.getProblemId());
        problem.setTitle(problemForm.getProblemName());
        problem.setTimeLimit(problemForm.getTimeLimit());
        problem.setMemoryLimit(problemForm.getMemoryLimit());
        problem.setLegend(problemForm.getLegend());
        problem.setInputFormat(problemForm.getInputFormat());
        problem.setOutputFormat(problemForm.getOutputFormat());
    }
}
