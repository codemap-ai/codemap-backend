package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ProblemSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemSetId; // primary key

    private String title;

    private Long duration; // minutes

    @OneToMany(mappedBy = "problemSet")
    private List<ProblemSetProblem> problemSetProblems = new ArrayList<>();
}
