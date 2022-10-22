package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.LoadCode;

import java.util.Optional;

public interface LoadCodeRepository {

    Optional<LoadCode> findById(Long loadCodeId);
    LoadCode findByContestIdAndProblemIdAndLanguage(Long contestId, Long problemId, String language);
    LoadCode save(LoadCode loadCode);
}
