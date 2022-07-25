package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Setter
@Getter
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submissionId; // primary key
    @Column(name = "problem_id")
    private int problemId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "contest_id")
    private int contestId;
    @Column(name = "execute_time")
    private float executeTime; // sec
    @Column(name = "used_memory")
    private int usedMemory; // KB
    @Column(name = "result")
    private int result; // 0->AC, 1->WA, 2->CompileError, 3->RuntimeError, 4->OutOfMemory, 5->SegmentationFault, 6->TimeOut
    @Column(name = "used_language")
    private int usedLanguage; // 0->C, 1->C++, 2->JAVA, 3->Python
    @Column(name = "submit_code",length = 100000)
    private String submitCode;
    @Column(name = "submit_date")
    private Date submitDate; // submission date
    @Column(name = "score")
    private int score;
}


