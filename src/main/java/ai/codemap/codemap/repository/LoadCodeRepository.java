package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.LoadCode;

import java.util.Optional;

public interface LoadCodeRepository {

    Optional<LoadCode> findById(Long loadCodeId);
    LoadCode findByContestIdAndProblemIdAndLanguage(int contestId, int problemId, String language);
    LoadCode save(LoadCode loadCode);
}
