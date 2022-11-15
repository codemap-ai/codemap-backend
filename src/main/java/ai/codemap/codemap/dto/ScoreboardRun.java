package ai.codemap.codemap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreboardRun {

    private Long id;
    private Long team;
    private Long problem;
    private String result;
    private Long submissionTime;
}
