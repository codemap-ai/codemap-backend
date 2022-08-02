package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.model.ProblemSetProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemSetProblemRepository {

    private final EntityManager em;

    public Long save(ProblemSetProblem problemSetProblem) {
        em.persist(problemSetProblem);
        return problemSetProblem.getProblemSetProblemId();
    }

    public void remove(Long id) {
        ProblemSetProblem problemSetProblem = em.find(ProblemSetProblem.class, id);
        em.remove(problemSetProblem);
    }
}
