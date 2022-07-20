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
    private int pid; // primary key

    @Column(name="cid")
    private int cid; // foreign key
    @Column(name="title")
    private String title;
    @Column(name="memoryLimit")
    private int memoryLimit; // KB
    @Column(name="timeLimit")
    private float timeLimit; // sec
    @Column(name="body")
    private String body;
}
