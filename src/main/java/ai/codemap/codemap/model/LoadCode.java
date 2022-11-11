package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class LoadCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loadCodeId;
    @Column(name = "contest_id")
    private Long contestId;
    @Column(name = "problem_id")
    private Long problemId;
    @Column(name = "code")
    private String code;
    @Column(name = "language")
    private String language;
}
