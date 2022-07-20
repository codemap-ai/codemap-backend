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
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid; // primary key
    @Column(name="pid")
    private int pid;
    @Column(name="uid")
    private int uid;
    @Column(name="executeTime")
    private float time; // sec
    @Column(name="usedMemory")
    private int memory; // KB
    @Column(name="result")
    private int result; // 0->AC, 1->WA, 2->CompileError, 3->RuntimeError, 4->OutOfMemory, 5->SegmentationFault, 6->TimeOut
    @Column(name="usedLanguage")
    private int language; // 0->C, 1->C++, 2->JAVA, 3->Python
    @Column(name="submitCode")
    private String code;
    @Column(name="submitDate")
    private Date date; // submission date
}
