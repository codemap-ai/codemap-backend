package ai.codemap.codemap.form;

import lombok.Data;

@Data
public class LoadCodeForm {
    private Long contestId;
    private Long problemId;
    private String language;
}
