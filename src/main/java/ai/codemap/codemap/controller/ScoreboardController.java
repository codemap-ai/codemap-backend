package ai.codemap.codemap.controller;

import ai.codemap.codemap.dto.ScoreboardContest;
import ai.codemap.codemap.dto.ScoreboardRuns;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScoreboardController {

    private final ScoreboardController scoreboardController;

    @GetMapping("/contests/spotboard/{problemSetId}/contest.json")
    public ScoreboardContest getContest(@PathVariable Long problemSetId) {
        return scoreboardController.getContest(problemSetId);
    }

    @GetMapping("/contests/spotboard/{problemSetId}/runs.json")
    public ScoreboardRuns getRuns(@PathVariable Long problemSetId) {
        return scoreboardController.getRuns(problemSetId);
    }
}
