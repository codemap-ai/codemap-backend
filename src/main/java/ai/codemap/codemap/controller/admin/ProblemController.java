package ai.codemap.codemap.controller.admin;

import ai.codemap.codemap.form.ProblemForm;
import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/admin/problem/update")
    public String updateProblem(ProblemForm problemForm) {
        problemService.updateProblem(problemForm);
        return "redirect:/admin/problem/" + problemForm.getProblemId();
    }

    @GetMapping("/admin/problems")
    public String getProblems(Model model) {
        List<Problem> problems = problemService.getAll();
        model.addAttribute("problems", problems);
        return "problems";
    }

    @GetMapping("/admin/problem/{problemId}")
    public String getProblem(@PathVariable("problemId") Long problemId, Model model) {
        Problem problem = problemService.getOne(problemId);

        ProblemForm problemForm = new ProblemForm();
        problemForm.setProblemId(problem.getProblemId());
        problemForm.setProblemName(problem.getTitle());
        problemForm.setTimeLimit(problem.getTimeLimit());
        problemForm.setMemoryLimit(problem.getMemoryLimit());
        problemForm.setLegend(problem.getLegend());
        problemForm.setInputFormat(problem.getInputFormat());
        problemForm.setOutputFormat(problem.getOutputFormat());

        model.addAttribute("problemForm", problemForm);
        return "problem";
    }

    @PostMapping("/admin/problem/create")
    public String createProblem() {
        Long problemId = problemService.createProblem();
        return "redirect:/admin/problem/" + problemId;
    }
}
