package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class ProblemSetProblem {

    @Id @GeneratedValue
    private Long problemSetProblemId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problemset_id")
    private ProblemSet problemSet;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
