package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.repository.MemoryProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {
    private MemoryProblemRepository problemRepository = new MemoryProblemRepository();

    public Problem getOne(int pid){
        return problemRepository.read(pid);
    }

    public List<Problem> getAll(){
        return problemRepository.getList();
    }
}
