package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
@Getter
public class Result {
    private int rid; // primary key
    private int pid;
    private int uid;
    private float time; // sec
    private int memory; // KB
    private int result; // 0->AC, 1->WA, 2->CompileError, 3->RuntimeError, 4->OutOfMemory, 5->SegmentationFault, 6->TimeOut
    private int language; // 0->C, 1->C++, 2->JAVA, 3->Python
    private String code;
    private Date date; // submission date
}
