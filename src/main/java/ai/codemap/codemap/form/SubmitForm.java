package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SubmitForm {
    private int userId;
    private int problemId;
    private int contestId;
    private int language;
    private String source;
}
