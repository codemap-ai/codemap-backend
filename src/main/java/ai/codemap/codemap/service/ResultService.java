package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Result;
import ai.codemap.codemap.repository.MemoryResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private final MemoryResultRepository resultRepository = new MemoryResultRepository();

    public Result getOne(int rid){
        return resultRepository.read(rid);
    }

    public List<Result> getAll(){
        return resultRepository.getList();
    }

    public List<Result> getByPid(int pid){
        return resultRepository.getByPid(pid);
    }

    public List<Result> getByUid(int uid){
        return resultRepository.getByUid(uid);
    }
}
