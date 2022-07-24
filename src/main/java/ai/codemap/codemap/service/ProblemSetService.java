package ai.codemap.codemap.service;

import ai.codemap.codemap.model.ProblemSet;
import ai.codemap.codemap.repository.ProblemSetRepository;

import java.util.List;

public class ProblemSetService {

    private final ProblemSetRepository problemSetRepository;

    public ProblemSetService(ProblemSetRepository problemSetRepository) {
        this.problemSetRepository = problemSetRepository;
    }

    public ProblemSet getOne(int problemSet_id) {
        return problemSetRepository.findById(problemSet_id);

    }

    public List<ProblemSet> getAll() {
        return problemSetRepository.findAll();

    }
}
