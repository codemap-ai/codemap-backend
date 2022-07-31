package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ProblemResource {
    // 하나의 문제에 속해있는 파일
    // 입출력 데이터, 소스 코드, 첨부파일(이미지) 등...
    // 실제 파일은 S3에 저장되어 있음

    @Id @GeneratedValue
    @Column(name = "problem_resource_id")
    private Long id;

    @Column(name = "problem_id")
    private Long problemId;

    private String name;

    @Column(name = "object_name")
    private String objectName; // S3에 등록되어 있는 이름

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        INPUT, OUTPUT, UNKNOWN
    }
}
