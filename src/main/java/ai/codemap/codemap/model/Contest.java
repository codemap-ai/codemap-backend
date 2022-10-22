package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contestId; // primary key

    @Column(name = "problem_set_id")
    private Long problemSetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_time")
    private Date createTime;

    @Column(name = "finish_time")
    private Date finishTime;

    @Column(name = "penalty")
    private Long penalty;
}
