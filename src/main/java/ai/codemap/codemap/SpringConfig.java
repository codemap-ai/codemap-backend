package ai.codemap.codemap;

import ai.codemap.codemap.repository.AlgorithmRepository;
import ai.codemap.codemap.repository.ContestRepository;
import ai.codemap.codemap.repository.ProblemRepository;
import ai.codemap.codemap.repository.ResultRepository;
import ai.codemap.codemap.service.AlgorithmService;
import ai.codemap.codemap.service.ContestService;
import ai.codemap.codemap.service.ProblemService;
import ai.codemap.codemap.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final AlgorithmRepository algorithmRepository;
    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public SpringConfig(AlgorithmRepository algorithmRepository, ContestRepository contestRepository, ProblemRepository problemRepository, ResultRepository resultRepository) {
        this.algorithmRepository = algorithmRepository;
        this.contestRepository = contestRepository;
        this.problemRepository = problemRepository;
        this.resultRepository = resultRepository;
    }

    @Bean
    public AlgorithmService algorithmService() {
        return new AlgorithmService(algorithmRepository);
    }

    @Bean
    public ContestService contestService() {
        return new ContestService(contestRepository);
    }

    @Bean
    public ProblemService problemService() {
        return new ProblemService(problemRepository);
    }

    @Bean
    public ResultService repositoryService() {
        return new ResultService(resultRepository);
    }
}
