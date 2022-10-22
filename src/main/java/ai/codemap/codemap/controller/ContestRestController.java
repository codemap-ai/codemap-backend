package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.FinishForm;
import ai.codemap.codemap.form.StartForm;
import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.service.ContestService;
import ai.codemap.codemap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/contests")
public class ContestRestController {
    private final ContestService contestService;
    private final UserService userService;
    @Autowired
    public ContestRestController(ContestService contestService, UserService userService) {
        this.contestService = contestService;
        this.userService = userService;
    }


    @PostMapping("/start")
    public ResponseEntity startContest(@RequestBody StartForm startForm) {
        Contest contest = new Contest();

        contest.setUser(userService.getCurrentUser());
        contest.setProblemSetId(startForm.getProblemSetId());
        contest.setCreateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        final Long contestId = contestService.addContest(contest);


        return ResponseEntity.ok(contestId);
    }


    @PostMapping("/finish")
    public ResponseEntity finishContest(@RequestBody FinishForm finishForm) {
        Contest contest = contestService.getOne(finishForm.getContestId());

        contest.setFinishTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        Long penalty = 0L;
        /*
            todo
            calculate penalty
        */
        contest.setPenalty(penalty);

        contestService.addContest(contest);

        return ResponseEntity.ok(contest);
    }

    @GetMapping("/{contestId}")
    public ResponseEntity getProblem(@PathVariable String contestId) {
        Contest contest = contestService.getOne(Integer.parseInt(contestId));

        if (contest == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(contest);
    }
}
