package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Data
@Getter
@Setter
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemId; // primary key

    @Column(name="contest_id")
    private int contestId; // foreign key
    @Column(name="title")
    private String title;
    @Column(name="memory_limit")
    private int memoryLimit; // KB
    @Column(name="time_limit")
    private float timeLimit; // sec
    @Column(name="body")
    private String body;
}
