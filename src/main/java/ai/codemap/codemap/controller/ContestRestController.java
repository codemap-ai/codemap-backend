package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.FinishForm;
import ai.codemap.codemap.form.ResponseForm;
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
    public ResponseForm startContest(@RequestBody StartForm startForm) {
        Contest contest = new Contest();

        contest.setUserId(1); // todo

        contest.setProblemSetId(startForm.getProblemSetId());
        contest.setCreateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        final int contestId = contestService.addContest(contest).getContestId();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(contestId));
        return responseForm;
    }


    @PostMapping("/finish")
    public ResponseForm finishContest(@RequestBody FinishForm finishForm) {
        Contest contest = contestService.getOne(finishForm.getContestId());

        contest.setFinishTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        int penalty = 0;
        /*
            todo
            calculate penalty
        */
        contest.setPenalty(penalty);

        contestService.addContest(contest);

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(contest));

        return responseForm;
    }

    @GetMapping("/{contest_id}")
    public ResponseForm getProblem(@PathVariable String contest_id) {
        Contest contest = contestService.getOne(Integer.parseInt(contest_id));

        ResponseForm responseForm = new ResponseForm();

        if (contest == null) {
            responseForm.setResponseEntity(ResponseEntity.badRequest().build());
            return responseForm;
        }

        responseForm.setResponseEntity(ResponseEntity.ok(contest));
        return responseForm;
    }
}
