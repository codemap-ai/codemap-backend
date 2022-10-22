package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.FinishForm;
import ai.codemap.codemap.form.StartForm;
import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.service.ContestService;
import ai.codemap.codemap.service.UserService;
import lombok.Data;
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
        contest.setCreateTime(LocalDateTime.now());

        final Long contestId = contestService.addContest(contest);

        return ResponseEntity.ok(contestId);
    }

    @PostMapping("/finish")
    public ResponseEntity finishContest(@RequestBody FinishForm finishForm) {
        Contest contest = contestService.getOne(finishForm.getContestId());

        contest.setFinishTime(LocalDateTime.now());

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
    public ContestResponse getContest(@PathVariable Long contestId) {
        Contest contest = contestService.getOne(contestId);
        ContestResponse response = new ContestResponse();

        response.setProblemSetId(contest.getProblemSetId());
        response.setCreateTime(contest.getCreateTime());
        response.setFinishTime(contest.getFinishTime());

        return response;
    }

    @Data
    static class ContestResponse {
        private Long problemSetId;
        private LocalDateTime createTime;
        private LocalDateTime finishTime;
    }
}
