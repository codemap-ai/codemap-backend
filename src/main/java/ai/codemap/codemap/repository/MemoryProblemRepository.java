package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Problem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemoryProblemRepository implements ProblemRepository {

    @Override
    public Problem read(int pid) {

        /* todo */

        return null;
    }

    @Override
    public List<Problem> getList() {

        /* todo */

        return null;
    }
}
