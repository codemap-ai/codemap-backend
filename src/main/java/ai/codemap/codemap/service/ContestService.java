package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.repository.ContestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public class ContestService {

    private final ContestRepository contestRepository;

    public ContestService(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    public Contest getOne(int cid) {
        return contestRepository.findById(cid);

    }

    public List<Contest> getAll() {
        return contestRepository.findAll();

    }
}
