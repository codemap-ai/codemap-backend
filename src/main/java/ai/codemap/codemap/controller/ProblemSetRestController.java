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
    public ResponseForm getProblemSetList() {

        List<ProblemSet> orgList = problemSetService.getAll();
        if (orgList == null) orgList = new ArrayList<ProblemSet>();

        List<ProblemSetForm> dstList = new ArrayList<>();

        for (ProblemSet org : orgList) {
            ProblemSetForm dst = new ProblemSetForm();
            dst.setProblemSetId(org.getProblemSetId());
            dst.setDuration(org.getDuration());
            dst.setTitle(org.getTitle());

            dstList.add(dst);
        }


        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(dstList));

        return responseForm;
    }

    @GetMapping("/{problem_set_id}")
    public ResponseForm getProblemSet(@PathVariable String problem_set_id) {
        ProblemSet problemSet = problemSetService.getOne(Long.parseLong(problem_set_id));
        ResponseForm responseForm = new ResponseForm();

        if (problemSet == null) {
            responseForm.setResponseEntity(ResponseEntity.badRequest().build());
            return responseForm;
        }

        ProblemSetForm problemSetForm = new ProblemSetForm();
        problemSetForm.setProblemSetId(problemSet.getProblemSetId());
        problemSetForm.setDuration(problemSet.getDuration());
        problemSetForm.setTitle(problemSet.getTitle());

        responseForm.setResponseEntity(ResponseEntity.ok(problemSetForm));
        return responseForm;
    }
}
