package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contestId; // primary key
    @Column(name = "problem_set_id")
    private int problemSetId;
    @Column(name = "user_id")
    private int userId;

}
