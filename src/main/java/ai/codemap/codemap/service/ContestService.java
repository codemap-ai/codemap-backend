package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.repository.MemoryContestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestService {
    private final MemoryContestRepository contestRepository = new MemoryContestRepository();

    public Contest getOne(int cid){
        return contestRepository.read(cid);
    }

    public List<Contest> getAll(){
        return contestRepository.getList();
    }
}
