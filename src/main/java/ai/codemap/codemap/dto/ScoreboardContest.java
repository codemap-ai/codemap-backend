package ai.codemap.codemap.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScoreboardContest {

    private String title;
    private String systemName;
    private String systemVersion;
    private List<ScoreboardProblem> problems;
    private List<ScoreboardTeam> teams;
}
