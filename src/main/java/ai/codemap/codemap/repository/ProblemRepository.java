package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Problem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProblemRepository {

    @PersistenceContext
    private EntityManager em;

    public Problem findById(Long problemId) {
        return em.find(Problem.class, problemId);
    }
    public List<Problem> findAll() {
        return em.createQuery("select e from Problem as e", Problem.class)
                .getResultList();
    }

    @Transactional
    public void save(Problem problem) {
        em.persist(problem);
    }

}
