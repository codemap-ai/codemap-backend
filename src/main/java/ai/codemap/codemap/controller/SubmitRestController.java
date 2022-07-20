package ai.codemap.codemap.controller;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit")
public class SubmitRestController {

    private final ProblemService problemService;

    @Autowired
    public SubmitRestController(ProblemService problemService){
        this.problemService = problemService;
    }

    @GetMapping("/{problem_id}")
    public ResponseEntity<Problem> submitForm(@PathVariable String problem_id){
        Problem problem = problemService.getOne(Integer.parseInt(problem_id));

        if(problem==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/{problem_id}")
    public ResponseEntity<SubmitForm> submit(@RequestBody SubmitForm submitForm){

        /* todo */

        return ResponseEntity.ok(submitForm);
    }

}
