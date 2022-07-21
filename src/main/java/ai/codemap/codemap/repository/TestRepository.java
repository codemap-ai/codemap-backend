package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Test;

import java.util.List;

public interface TestRepository {
    Test findById(int testId);
    List<Test> findAll();
}
