package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SubmitForm {
    private int problemId;
    private int contestId;
    private Boolean testMode;
    private String input;
    private String language;
    private String source;
}
