package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.ProblemSetForm;
import ai.codemap.codemap.form.ResponseForm;
import ai.codemap.codemap.model.ProblemSet;
import ai.codemap.codemap.service.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/problem_sets")
public class ProblemSetRestController {
    private final ProblemSetService problemSetService;

    @Autowired
    public ProblemSetRestController(ProblemSetService problemSetService) {
        this.problemSetService = problemSetService;
    }

    @GetMapping("")
    public ResponseEntity getProblemSetList() {

        List<ProblemSet> orgList = problemSetService.getAll();
        if (orgList == null) orgList = new ArrayList<ProblemSet>();

        List<ProblemSetForm> dstList = new ArrayList<>();

        for (ProblemSet org : orgList) {
            ProblemSetForm dst = new ProblemSetForm();
            dst.setProblemSetId(org.getProblemSetId());
            dst.setDuration(org.getDuration());
            dst.setTitle(org.getTitle());
            dst.setProblem_list(List.of(org.getProblem_list().split(",")));

            dstList.add(dst);
        }

        return ResponseEntity.ok(dstList);
    }

    @GetMapping("/{problem_set_id}")
    public ResponseEntity getProblemSet(@PathVariable String problem_set_id) {
        ProblemSet problemSet = problemSetService.getOne(Integer.parseInt(problem_set_id));


        if (problemSet == null) {
            return ResponseEntity.badRequest().build();
        }

        ProblemSetForm problemSetForm = new ProblemSetForm();
        problemSetForm.setProblemSetId(problemSet.getProblemSetId());
        problemSetForm.setDuration(problemSet.getDuration());
        problemSetForm.setTitle(problemSet.getTitle());
        problemSetForm.setProblem_list(List.of(problemSet.getProblem_list().split(",")));

        return ResponseEntity.ok(problemSetForm);
    }
}
