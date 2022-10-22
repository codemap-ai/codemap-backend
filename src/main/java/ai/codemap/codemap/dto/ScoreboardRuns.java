package ai.codemap.codemap.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScoreboardRuns {

    private ScoreboardTime time;
    private List<ScoreboardRun> runs;
}
