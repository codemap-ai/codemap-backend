package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class Contest {
    private int cid; // primary key
    private String title;
    private List<Integer> problemSet;
}
