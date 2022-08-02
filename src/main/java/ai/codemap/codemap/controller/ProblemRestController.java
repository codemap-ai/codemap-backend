package ai.codemap.codemap.controller;

import ai.codemap.codemap.dto.SimpleProblemDto;
import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.service.ProblemService;
import ai.codemap.codemap.service.S3Service;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ai.codemap.codemap.model.ProblemResource.Type.INPUT;
import static ai.codemap.codemap.model.ProblemResource.Type.OUTPUT;

@RestController
public class ProblemRestController {
    private final ProblemService problemService;
    private final S3Service s3Service;

    public ProblemRestController(ProblemService problemService, S3Service s3Service) {
        this.problemService = problemService;
        this.s3Service = s3Service;
    }

    @GetMapping("/problems")
    public List<SimpleProblemDto> getProblemList() {
        return problemService.getSimpleList();
    }

    @GetMapping("/problems/{problemId}")
    public ProblemResponse getProblem(@PathVariable Long problemId) {
        Problem problem = problemService.getOne(problemId);

        List<String> inputExamples = s3Service.getExamples(problemId, INPUT);
        List<String> outputExamples = s3Service.getExamples(problemId, OUTPUT);

        ProblemResponse response = new ProblemResponse();
        response.setProblemId(problem.getProblemId());
        response.setTitle(problem.getTitle());
        response.setMemoryLimit(problem.getMemoryLimit());
        response.setTimeLimit(problem.getTimeLimit());
        response.setLegend(problem.getLegend());
        response.setInputFormat(problem.getInputFormat());
        response.setOutputFormat(problem.getOutputFormat());
        response.setInputExamples(inputExamples);
        response.setOutputExamples(outputExamples);

        return response;
    }

    @Data
    static class ProblemResponse {
        private Long problemId;
        private String title;
        private Long memoryLimit;
        private Double timeLimit;
        private String legend;
        private String inputFormat;
        private String outputFormat;
        private List<String> inputExamples;
        private List<String> outputExamples;
    }
}
