package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
public class LoadCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loadCodeId;
    @Column(name = "contest_id")
    private int contestId;
    @Column(name = "problem_id")
    private int problemId;
    @Column(name = "code")
    private String code;
}
