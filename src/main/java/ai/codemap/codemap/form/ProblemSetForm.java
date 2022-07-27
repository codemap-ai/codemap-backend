package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

/* Front로 보낼 ProblemSet Form
*  기존 ProblemSet에서 problem_list를 다루는 type 변경 (String -> List)
*
* */

@Data
@Getter
@Setter
public class ProblemSetForm {
    private int problemSetId; // primary key
    private String title;
    private List<String> problem_list;
    private int duration;
}
