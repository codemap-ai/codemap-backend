package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.model.ProblemSet;
import ai.codemap.codemap.model.ProblemSetProblem;
import ai.codemap.codemap.repository.ProblemRepository;
import ai.codemap.codemap.repository.ProblemSetProblemRepository;
import ai.codemap.codemap.repository.ProblemSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProblemSetService {

    private final ProblemSetRepository problemSetRepository;
    private final ProblemRepository problemRepository;
    private final ProblemSetProblemRepository problemSetProblemRepository;

    @Autowired
    public ProblemSetService(ProblemSetRepository problemSetRepository, ProblemRepository problemRepository, ProblemSetProblemRepository problemSetProblemRepository) {
        this.problemSetRepository = problemSetRepository;
        this.problemRepository = problemRepository;
        this.problemSetProblemRepository = problemSetProblemRepository;
    }

    public ProblemSet getOne(Long problemSetId) {
        return problemSetRepository.findById(problemSetId);
    }

    public List<ProblemSet> getAll() {
        return problemSetRepository.findAll();
    }

    public Long createProblemSet() {
        ProblemSet problemSet = new ProblemSet();
        problemSet.setTitle("New Problemset");
        problemSet.setDuration(90L);
        return problemSetRepository.save(problemSet);
    }

    public void addProblem(Long problemSetId, Long problemId) {
        ProblemSet problemSet = problemSetRepository.findById(problemSetId);
        Problem problem = problemRepository.findById(problemId);
        ProblemSetProblem e = new ProblemSetProblem();
        e.setProblemSet(problemSet);
        e.setProblem(problem);
        problemSet.getProblemSetProblems().add(e);
        problemSetProblemRepository.save(e);
    }

    public void popProblem(Long problemSetProblemId) {
        problemSetProblemRepository.remove(problemSetProblemId);
    }

    public void update(Long problemSetId, String title, Long duration) {
        ProblemSet problemSet = problemSetRepository.findById(problemSetId);
        problemSet.setTitle(title);
        problemSet.setDuration(duration);
    }
}
