package ai.codemap.codemap.repository;

import ai.codemap.codemap.dto.SimpleProblemDto;
import ai.codemap.codemap.model.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemRepository {

    private final EntityManager em;

    public Problem findById(Long problemId) {
        return em.find(Problem.class, problemId);
    }
    public List<Problem> findAll() {
        return em.createQuery("select e from Problem as e", Problem.class)
                .getResultList();
    }

    public void save(Problem problem) {
        em.persist(problem);
    }

    public List<SimpleProblemDto> getSimpleList() {
        return em.createQuery("select new ai.codemap.codemap.dto.SimpleProblemDto(p.problemId, p.title) from Problem p", SimpleProblemDto.class)
                .getResultList();
    }

}
