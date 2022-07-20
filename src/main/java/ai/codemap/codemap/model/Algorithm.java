package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Algorithm {
    private int aid; // primary key
    private String title;
    private String body;
}
