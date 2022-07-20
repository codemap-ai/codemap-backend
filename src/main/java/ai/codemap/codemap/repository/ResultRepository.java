package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Result;

import java.util.List;

public interface ResultRepository {
    Result findById(int rid);
    List<Result> findAll();
    List<Result> findByPid(int pid);
    List<Result> findByUid(int uid);
}
