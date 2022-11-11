package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SaveCodeForm {
    private Long contestId;
    private Long problemId;
    private String code;
    private String language;
}
