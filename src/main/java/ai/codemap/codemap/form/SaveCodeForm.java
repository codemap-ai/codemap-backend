package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SaveCodeForm {
    private int contestId;
    private int problemId;
    private String code;
    private String language;
}
