package ai.codemap.codemap.dto;

import lombok.Data;

@Data
public class ScoreboardRun {

    private Long id;
    private Long team;
    private Long problem;
    private String result;
    private Long submissionTime;
}
