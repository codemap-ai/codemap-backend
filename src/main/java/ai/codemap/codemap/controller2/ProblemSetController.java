package ai.codemap.codemap.controller2;

import ai.codemap.codemap.model.ProblemSet;
import ai.codemap.codemap.model.ProblemSetProblem;
import ai.codemap.codemap.service.ProblemSetService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemSetController {

    private final ProblemSetService problemSetService;

    @GetMapping("/admin/problemset/{problemSetId}")
    public String getProblemSet(@PathVariable Long problemSetId, Model model) {
        ProblemSet problemSet = problemSetService.getOne(problemSetId);
        List<ProblemDto> problems = problemSet.getProblemSetProblems().stream()
                .map(ProblemSetProblem::getProblem)
                .map(problem -> new ProblemDto(problem.getProblemId(), null, problem.getTitle()))
                .toList();
        for (int i = 0; i < problemSet.getProblemSetProblems().size(); i++) {
            Long id = problemSet.getProblemSetProblems().get(i).getProblemSetProblemId();
            problems.get(i).setProblemSetProblemId(id);
        }

        ProblemSetForm problemSetForm = new ProblemSetForm(problemSetId, problemSet.getTitle(), problemSet.getDuration());

        model.addAttribute("problemSetForm", problemSetForm);
        model.addAttribute("problemSetId", problemSetId);
        model.addAttribute("problems", problems);
        return "problemset";
    }

    @PostMapping("/admin/problemset/{problemSetId}/update")
    public String updateProblemSet(@PathVariable Long problemSetId, @Valid ProblemSetForm form) {
        problemSetService.update(form.getProblemSetId(), form.getTitle(), form.getDuration());
        return "redirect:/admin/problemset/" + problemSetId;
    }

    @PostMapping("/admin/problemset/{problemSetId}/add")
    public String addProblem(@PathVariable Long problemSetId, @RequestParam Long problemId) {
        problemSetService.addProblem(problemSetId, problemId);
        return "redirect:/admin/problemset/" + problemSetId;
    }

    @PostMapping("/admin/problemset/{problemSetId}/pop")
    public String popProblem(@PathVariable Long problemSetId, @RequestParam Long problemSetProblemId) {
        problemSetService.popProblem(problemSetProblemId);
        return "redirect:/admin/problemset/" + problemSetId;
    }

    @GetMapping("/admin/problemsets")
    public String getProblemSets(Model model) {
        List<ProblemSet> problemSets = problemSetService.getAll();
        model.addAttribute("problemSets", problemSets);
        return "problemsets";
    }

    @PostMapping("/admin/problemsets/create")
    public String createProblemSet() {
        Long id = problemSetService.createProblemSet();
        return "redirect:/admin/problemset/" + id;
    }

    @Data
    @AllArgsConstructor
    static class ProblemSetForm {

        private Long problemSetId;

        @NotBlank
        private String title;
        private Long duration;
    }

    @Data
    @AllArgsConstructor
    static class ProblemDto {

        private Long problemId;
        private Long problemSetProblemId;
        private String title;
    }
}
