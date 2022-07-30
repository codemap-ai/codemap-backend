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
    private Long problemId; // primary key

    @Column(name="title",length = 100)
    private String title;

    @Column(name="memory_limit")
    private Long memoryLimit; // KiB

    @Column(name="time_limit")
    private Double timeLimit; // sec

    @Column(name="body",length = 100000)
    private String body;
}
