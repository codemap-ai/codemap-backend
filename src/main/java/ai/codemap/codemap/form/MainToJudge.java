package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MainToJudge {
    private Long id;
    private int problemId;
    private String input;
    private Boolean testMode;
    private String language;
    private String source;
}
