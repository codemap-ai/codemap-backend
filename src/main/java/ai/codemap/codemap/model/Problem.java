package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long problemId; // primary key

    @Column(name="title",length = 100)
    private String title;

    private String checker;

    @Column(name="memory_limit")
    private Long memoryLimit; // MiB

    @Column(name="time_limit")
    private Double timeLimit; // sec

    @Column(name="legend",length = 100000)
    private String legend;

    @Column(name="input_format",length = 100000)
    private String inputFormat;

    @Column(name="output_format",length = 100000)
    private String outputFormat;
}
