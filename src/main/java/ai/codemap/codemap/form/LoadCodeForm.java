package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoadCodeForm {
    private int contestId;
    private int problemId;
    private String language;
}
