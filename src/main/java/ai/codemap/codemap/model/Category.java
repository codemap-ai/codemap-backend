package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long categoryId;

    private String title;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Algorithm> algorithms = new ArrayList<>();
}
