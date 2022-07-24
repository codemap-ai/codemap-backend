package ai.codemap.codemap.controller;
import ai.codemap.codemap.model.ProblemSet;
import ai.codemap.codemap.service.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/problem_sets")
public class ProblemSetRestController {
    private final ProblemSetService problemSetService;

    @Autowired
    public ProblemSetRestController(ProblemSetService problemSetService){
        this.problemSetService = problemSetService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProblemSet>> getProblemSetList(){

        List<ProblemSet> list = problemSetService.getAll();
        if(list == null) list = new ArrayList<ProblemSet>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{problem_set_id}")
    public ResponseEntity<ProblemSet> getProblemSet(@PathVariable String problem_set_id){
        ProblemSet contest = problemSetService.getOne(Integer.parseInt(problem_set_id));
        if(contest == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(contest);
    }
}
