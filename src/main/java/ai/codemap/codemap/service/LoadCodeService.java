package ai.codemap.codemap.service;

import ai.codemap.codemap.model.LoadCode;
import ai.codemap.codemap.repository.LoadCodeRepository;

public class LoadCodeService {
    private LoadCodeRepository loadCodeRepository;
    public LoadCodeService(LoadCodeRepository loadCodeRepository) {
        this.loadCodeRepository = loadCodeRepository;
    }

    public LoadCode addLoadCode(LoadCode loadCode){
        return loadCodeRepository.save(loadCode);
    }

    public LoadCode getByContestIdAndProblemId(int contestId, int problemId, String language){
        return loadCodeRepository.findByContestIdAndProblemIdAndLanguage(contestId,problemId,language);
    }

}
