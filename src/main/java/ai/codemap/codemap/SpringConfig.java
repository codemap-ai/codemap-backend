package ai.codemap.codemap;

import ai.codemap.codemap.repository.*;
import ai.codemap.codemap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final AlgorithmRepository algorithmRepository;
    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final TestRepository testRepository;
    @Autowired
    public SpringConfig(AlgorithmRepository algorithmRepository, ContestRepository contestRepository, ProblemRepository problemRepository, SubmissionRepository submissionRepository, TestRepository testRepository) {
        this.algorithmRepository = algorithmRepository;
        this.contestRepository = contestRepository;
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.testRepository = testRepository;
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
    public SubmissionService repositoryService() {
        return new SubmissionService(submissionRepository);
    }
    @Bean
    public TestService testService(){
        return new TestService(testRepository);
    }
}
