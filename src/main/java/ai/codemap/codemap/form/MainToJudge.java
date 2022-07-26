package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MainToJudge {
    private int id;
    private int problemId;
    private String language;
    private String source;
}
