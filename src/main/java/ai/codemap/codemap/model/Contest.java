package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.persistence.*;
@Data
@Getter
@Setter
@Entity
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid; // primary key

    @Column(name="title")
    private String title;

    @Column(name="problemSet")
    private String problemSet;
}
