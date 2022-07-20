package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Algorithm;

import java.util.List;

public interface AlgorithmRepository {
    Algorithm findById(int aid);
    List<Algorithm> findAll();
}
