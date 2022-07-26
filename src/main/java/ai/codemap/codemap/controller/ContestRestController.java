package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.FinishForm;
import ai.codemap.codemap.form.StartForm;
import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@RestController
@RequestMapping("/contests")
public class ContestRestController {
    private final ContestService contestService;

    @Autowired
    public ContestRestController(ContestService contestService) {
        this.contestService = contestService;
    }


    @PostMapping("/start")
    public ResponseEntity<Integer> startContest(@RequestBody StartForm startForm){
        Contest contest = new Contest();

        contest.setUserId(startForm.getUserId());
        contest.setProblemSetId(startForm.getProblemSetId());
        contest.setCreateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        final int contestId = contestService.addContest(contest).getContestId();

        return ResponseEntity.ok(contestId);
    }


    @PostMapping("/finish")
    public ResponseEntity<Contest> finishContest(@RequestBody FinishForm finishForm){
        Contest contest = contestService.getOne(finishForm.getContestId());

        contest.setFinishTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        int penalty = 0;
        /*
            todo
            calculate penalty
        */
        contest.setPenalty(penalty);

        contestService.addContest(contest);
        return ResponseEntity.ok(contest);
    }
    @GetMapping("/{contest_id}")
    public ResponseEntity<Contest> getProblem(@PathVariable String contest_id) {
        Contest contest = contestService.getOne(Integer.parseInt(contest_id));
        if (contest == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(contest);
    }
}
