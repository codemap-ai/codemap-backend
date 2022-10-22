package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JudgeToMain {

    private Long submissionId;
    private String status;
    private Double time;
    private Long memory;
    private String compilerMessage;
    private String output;
    private Long score;
}
