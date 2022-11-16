package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Contest;
import ai.codemap.codemap.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContestRepository {

    private final EntityManager em;

    public Contest findById(Long contestId) {
        return em.find(Contest.class, contestId);
    }
    public List<Contest> findAll() {
        return em.createQuery("select c from Contest c", Contest.class)
                .getResultList();
    }

    public Long save(Contest contest) {
        em.persist(contest);
        return contest.getContestId();
    }

    public List<User> getUsersByProblemSetId(Long problemSetId) {
        return em.createQuery("select distinct c.user from Contest c where c.problemSetId = :problemSetId", User.class)
                .setParameter("problemSetId", problemSetId)
                .getResultList();
    }

    public List<Contest> findByProblemSetId(Long problemSetId) {



        return em.createQuery("select c from Contest c where c.problemSetId = :problemSetId", Contest.class)
                .setParameter("problemSetId", problemSetId)
                .getResultList();
    }

    public List<Contest> findByUserId(Long userId){
        User user = em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();

        return em.createQuery("select c from Contest c where c.user = :user", Contest.class)
                        .setParameter("user", user)
                        .getResultList();
    }
}
