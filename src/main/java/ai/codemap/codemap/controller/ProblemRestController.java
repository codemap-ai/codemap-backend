package ai.codemap.codemap.controller;


import ai.codemap.codemap.form.ResponseForm;
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
    public ResponseForm getProblemList() {

        List<Problem> list = problemService.getAll();
        if (list == null) list = new ArrayList<Problem>();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(list));

        return responseForm;
    }

    @GetMapping("/{problem_id}")
    public ResponseForm getProblem(@PathVariable String problem_id) {
        Problem problem = problemService.getOne(Integer.parseInt(problem_id));

        ResponseForm responseForm = new ResponseForm();
        if (problem == null) {

            responseForm.setResponseEntity(ResponseEntity.badRequest().build());
            return responseForm;
        }

        ;
        responseForm.setResponseEntity(ResponseEntity.ok(problem));
        return responseForm;
    }

}
