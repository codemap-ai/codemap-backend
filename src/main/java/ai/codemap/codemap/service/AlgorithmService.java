package ai.codemap.codemap.service;

import ai.codemap.codemap.repository.MemoryAlgorithmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ai.codemap.codemap.model.Algorithm;

import java.util.List;

@Service
public class AlgorithmService {

    private final MemoryAlgorithmRepository algorithmRepository = new MemoryAlgorithmRepository();

    public Algorithm getOne(int aid){
            return algorithmRepository.read(aid);
        }
    public List<Algorithm> getALL(){
        return algorithmRepository.getList();
    }


}
