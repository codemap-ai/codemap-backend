package ai.codemap.codemap.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JudgeToMain {
    private int submissionId;
    private int status;
    private float time;
    private int memory;
    private String compilerMessage;
    private int score;
}
