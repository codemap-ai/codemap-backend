package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Result;

import java.util.List;

public interface ResultRepository {
    Result read(int rid);
    List<Result> getList();
    List<Result> getByPid(int pid);
    List<Result> getByUid(int uid);
}
