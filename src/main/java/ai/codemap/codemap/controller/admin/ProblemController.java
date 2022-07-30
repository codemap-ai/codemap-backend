package ai.codemap.codemap.controller.admin;

import ai.codemap.codemap.form.ProblemForm;
import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProblemController {

    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/admin/problems/update")
    public String updateProblem(ProblemForm problemForm) {
        Problem problem = problemService.getOne(problemForm.getProblemId());
        problem.setTitle(problemForm.getProblemName());
        problem.setTimeLimit(problemForm.getTimeLimit());
        problem.setMemoryLimit(problemForm.getMemoryLimit());
        problem.setBody(problemForm.getLegend());
        return "redirect:/admin/problem/" + problem.getProblemId();
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

        problemForm.setLegend(problem.getBody()); // TODO: Problem Entity 수정하기
        problemForm.setInputFormat(problem.getBody());
        problemForm.setOutputFormat(problem.getBody());

        model.addAttribute("problemForm", problemForm);
        return "problem";
    }

    @PostMapping("/admin/problem/create")
    public String createProblem() {
        Long problemId = problemService.createProblem();
        return "redirect:/admin/problem/" + problemId;
    }
}
