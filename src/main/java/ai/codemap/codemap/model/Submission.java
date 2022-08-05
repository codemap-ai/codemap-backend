package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Setter
@Getter
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId; // primary key
    @Column(name = "problem_id")
    private int problemId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "contest_id")
    private int contestId;
    @Column(name = "execute_time")
    private double executeTime; // sec
    @Column(name = "used_memory")
    private Long usedMemory; // KB
    @Column(name = "result")
    private String result; // 0->AC, 1->WA, 2->CompileError, 3->RuntimeError, 4->OutOfMemory, 5->SegmentationFault, 6->TimeOut
    @Column(name = "used_language")
    private String usedLanguage; // 0->C, 1->C++, 2->JAVA, 3->Python
    @Column(name = "compiler_message", length = 100000)
    private String compilerMessage;
    @Column(name = "submit_code",length = 100000)
    private String submitCode;
    @Column(name = "submit_date")
    private Date submitDate; // submission date
    @Column(name = "score")
    private int score;
    @Column(name = "testmode")
    private Boolean testMode;
    @Column(name = "input")
    private String input;
    @Column(name = "output")
    private String output;
}


