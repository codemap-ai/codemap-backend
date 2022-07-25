package ai.codemap.codemap.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SubmitForm {
    private int user_id;
    private int problem_id;
    private int language;
    private String source;
}
