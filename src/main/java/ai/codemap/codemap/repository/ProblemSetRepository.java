package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.ProblemSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemSetRepository {

    private final EntityManager em;

    public ProblemSet findById(Long problemSetId) {
        return em.find(ProblemSet.class, problemSetId);
    }
    public List<ProblemSet> findAll() {
        return em.createQuery("select s from ProblemSet s", ProblemSet.class)
                .getResultList();
    }

    public Long save(ProblemSet problemSet) {
        em.persist(problemSet);
        return problemSet.getProblemSetId();
    }
}
