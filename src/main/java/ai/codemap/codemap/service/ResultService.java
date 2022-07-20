package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Result;
import ai.codemap.codemap.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Result getOne(int rid){
        return resultRepository.findById(rid);
    }

    public List<Result> getAll(){
        return resultRepository.findAll();
    }

    public List<Result> getByPid(int pid){
        return resultRepository.findByPid(pid);
    }

    public List<Result> getByUid(int uid){
        return resultRepository.findByUid(uid);
    }
}
