package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.LoadCode;

import java.util.Optional;

public interface LoadCodeRepository {

    Optional<LoadCode> findById(Long loadCodeId);
    LoadCode findByContestIdAndProblemId(int contestId, int problemId);
    LoadCode save(LoadCode loadCode);
}
