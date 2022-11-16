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

import java.time.OffsetDateTime;

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
        contest.setCreateTime(OffsetDateTime.now());

        final Long contestId = contestService.addContest(contest);

        return ResponseEntity.ok(contestId);
    }

    @PostMapping("/finish")
    public ContestResponse finishContest(@RequestBody FinishForm finishForm) {
        Contest contest = contestService.getOne(finishForm.getContestId());

        contest.setFinishTime(OffsetDateTime.now());

        // TODO: calculate penalty
        Long penalty = 0L;
        contest.setPenalty(penalty);

        contestService.addContest(contest);

        ContestResponse response = new ContestResponse();

        response.setProblemSetId(contest.getProblemSetId());
        response.setCreateTime(contest.getCreateTime());
        response.setFinishTime(contest.getFinishTime());

        return response;
    }

    @GetMapping("/{contestId}")
    public ContestResponse getContest(@PathVariable Long contestId) {
        Contest contest = contestService.getOne(contestId);
        ContestResponse response = new ContestResponse();

        response.setProblemSetId(contest.getProblemSetId());
        response.setCreateTime(contest.getCreateTime());
        response.setFinishTime(contest.getFinishTime());

        System.out.println(contest.getUser().getUserId());
        return response;
    }

    @Data
    static class ContestResponse {
        private Long problemSetId;
        private OffsetDateTime createTime;
        private OffsetDateTime finishTime;
    }
}
