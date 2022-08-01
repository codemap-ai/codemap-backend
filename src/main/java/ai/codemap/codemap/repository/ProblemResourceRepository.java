package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.ProblemResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemResourceRepository {

    private final EntityManager em;

    public void save(ProblemResource problemResource) {
        em.persist(problemResource);
    }

    public void remove(Long problemResourceId) {
        ProblemResource problemResource = em.find(ProblemResource.class, problemResourceId);
        em.remove(problemResource);
    }

    public List<ProblemResource> findByProblemId(Long problemId) {
        return em.createQuery("select r from ProblemResource as r where r.problemId = :problemId", ProblemResource.class)
                .setParameter("problemId", problemId)
                .getResultList();
    }

    public ProblemResource findOne(Long id) {
        return em.find(ProblemResource.class, id);
    }

    public List<ProblemResource> findExamples(Long problemId, ProblemResource.Type type) {
        return em.createQuery("select r from ProblemResource r " +
                "where r.problemId = :problemId and r.type = :type and r.isExample = true " +
                "order by r.name asc", ProblemResource.class)
                .setParameter("problemId", problemId)
                .setParameter("type", type)
                .getResultList();
    }
}
