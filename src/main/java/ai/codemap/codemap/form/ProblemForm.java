package ai.codemap.codemap.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProblemForm {

    private Long problemId;
    private String problemName;
    private Double timeLimit;
    private Long memoryLimit;
    private String legend;
    private String inputFormat;
    private String outputFormat;
}
