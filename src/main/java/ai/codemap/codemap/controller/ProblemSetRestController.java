package ai.codemap.codemap.controller;

import ai.codemap.codemap.dto.SimpleProblemDto;
import ai.codemap.codemap.model.ProblemSet;
import ai.codemap.codemap.model.ProblemSetProblem;
import ai.codemap.codemap.service.ProblemSetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProblemSetRestController {
    private final ProblemSetService problemSetService;

    @GetMapping("/problem_sets")
    public List<SimpleProblemSet> getProblemSetList() {
        return problemSetService.getAll().stream()
                .map(p -> new SimpleProblemSet(p.getProblemSetId(), p.getTitle(), p.getDuration()))
                .toList();
    }

    @GetMapping("/problem_sets/{problemSetId}")
    public ProblemSetResponse getProblemSet(@PathVariable Long problemSetId) {
        ProblemSet problemSet = problemSetService.getOne(problemSetId);

        List<SimpleProblemDto> problemList = problemSet.getProblemSetProblems().stream()
                .map(ProblemSetProblem::getProblem)
                .map(problem -> new SimpleProblemDto(problem.getProblemId(), problem.getTitle()))
                .toList();

        ProblemSetResponse response = new ProblemSetResponse();
        response.setProblemSetId(problemSet.getProblemSetId());
        response.setTitle(problemSet.getTitle());
        response.setProblemList(problemList);
        response.setDuration(problemSet.getDuration());

        return response;
    }

    @Data
    @AllArgsConstructor
    static class SimpleProblemSet {
        private Long problemSetId;
        private String title;
        private Long duration;
    }

    @Data
    static class ProblemSetResponse {
        private Long problemSetId;
        private String title;
        private List<SimpleProblemDto> problemList;
        private Long duration;
    }
}
