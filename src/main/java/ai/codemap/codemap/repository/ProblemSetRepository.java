package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.ProblemSet;

import java.util.List;

public interface ProblemSetRepository {
    ProblemSet findById(int problemSetId);
    List<ProblemSet> findAll();
}
