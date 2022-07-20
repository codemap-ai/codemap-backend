package ai.codemap.codemap.controller;
import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contests")
public class ContestRestController {
    private final ContestService contestService;

    @Autowired
    public ContestRestController(ContestService contestService){
        this.contestService = contestService;
    }

    @GetMapping("")
    public ResponseEntity<List<Contest>> getContestList(){

        List<Contest> list = contestService.getAll();
        if(list == null) list = new ArrayList<Contest>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{contest_id}")
    public ResponseEntity<Contest> getContest(@PathVariable String contest_id){
        Contest contest = contestService.getOne(Integer.parseInt(contest_id));
        if(contest == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(contest);
    }
}