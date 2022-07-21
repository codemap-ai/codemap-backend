package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Problem;

import java.util.List;

public interface ProblemRepository {
    Problem findById(int problemId);
    List<Problem> findAll();

}
