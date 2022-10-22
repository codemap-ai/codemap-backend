package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.repository.ContestRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class ContestService {
    private ContestRepository contestRepository;
    public ContestService(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    public Long addContest(Contest contest) {
        return contestRepository.save(contest);
    }
    public Contest getOne(Long contestId){
        return contestRepository.findById(contestId);
    }

    public List<Contest> getAll(){
        return contestRepository.findAll();
    }
}
