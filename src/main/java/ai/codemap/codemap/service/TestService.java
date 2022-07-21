package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Test;
import ai.codemap.codemap.repository.TestRepository;

import java.util.List;

public class TestService {
    private TestRepository testRepository;
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public Test getOne(int test_id){
        return testRepository.findById(test_id);
    }

    public List<Test> getAll(){
        return testRepository.findAll();
    }
}
