package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Getter
@Setter
@Entity
public class ProblemSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemSetId; // primary key
    @Column(name="title")
    private String title;
    @Column(name="problem_list")
    private String problem_list;
}
