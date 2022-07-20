package ai.codemap.codemap.controller;


import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemRestController {
    private final ProblemService problemService;

    @Autowired
    public ProblemRestController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping("")
    public ResponseEntity<List<Problem>> getProblemList() {

        List<Problem> list = problemService.getAll();
        if (list == null) list = new ArrayList<Problem>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{problem_id}")
    public ResponseEntity<Problem> getProblem(@PathVariable String problem_id) {
        Problem problem = problemService.getOne(Integer.parseInt(problem_id));
        if (problem == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(problem);
    }

}
