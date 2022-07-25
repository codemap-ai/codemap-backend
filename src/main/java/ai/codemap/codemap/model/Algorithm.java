package ai.codemap.codemap.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
public class Algorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int algorithmId; // primary key

    @Column(name = "title", length = 100)
    private String title;
    @Column(name = "body", length = 100000)
    private String body;
    @Column(name = "description", length = 10000)
    private String description;
}
