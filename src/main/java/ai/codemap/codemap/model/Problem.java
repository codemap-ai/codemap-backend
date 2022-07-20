package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Problem {
    private int pid; // primary key
    private int cid; // foreign key
    private String title;
    private int memoryLimit; // KB
    private float timeLimit; // sec
    private String body;
}
