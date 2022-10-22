package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.repository.ContestRepository;

import java.util.List;

public class ContestService {
    private ContestRepository contestRepository;
    public ContestService(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    public Long addContest(Contest contest) {
        return contestRepository.save(contest);
    }
    public Contest getOne(int contest_id){
        return contestRepository.findById(contest_id);
    }

    public List<Contest> getAll(){
        return contestRepository.findAll();
    }
}
