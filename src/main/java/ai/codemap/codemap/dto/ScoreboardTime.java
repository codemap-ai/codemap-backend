package ai.codemap.codemap.dto;

import lombok.Data;

@Data
public class ScoreboardTime {

    private Long contestTime;
    private Boolean noMoreUpdate;
    private Long timeStamp;
}
