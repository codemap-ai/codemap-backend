package ai.codemap.codemap;

import ai.codemap.codemap.repository.*;
import ai.codemap.codemap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final AlgorithmRepository algorithmRepository;
    private final ProblemSetRepository problemSetRepository;
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final ContestRepository contestRepository;
    @Autowired
    public SpringConfig(AlgorithmRepository algorithmRepository, ProblemSetRepository problemSetRepository, ProblemRepository problemRepository, SubmissionRepository submissionRepository, ContestRepository contestRepository) {
        this.algorithmRepository = algorithmRepository;
        this.problemSetRepository = problemSetRepository;
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.contestRepository = contestRepository;
    }

    @Bean
    public AlgorithmService algorithmService() {
        return new AlgorithmService(algorithmRepository);
    }

    @Bean
    public ProblemSetService problemSetService() {
        return new ProblemSetService(problemSetRepository);
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
    public ContestService testService(){
        return new ContestService(contestRepository);
    }
}
