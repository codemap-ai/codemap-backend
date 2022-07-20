package ai.codemap.codemap.service;

import ai.codemap.codemap.repository.AlgorithmRepository;
import org.springframework.stereotype.Service;
import ai.codemap.codemap.model.Algorithm;

import java.util.List;

public class AlgorithmService {
    private final AlgorithmRepository algorithmRepository;
    public AlgorithmService(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public Algorithm getOne(int aid){
            return algorithmRepository.findById(aid);
        }
    public List<Algorithm> getALL(){
        return algorithmRepository.findAll();
    }


}
