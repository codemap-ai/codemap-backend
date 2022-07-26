package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JudgeToMain {
    private int submissionId;
    private String status;
    private double time;
    private Long memory;
    private String compilerMessage;
    private int score;
}
