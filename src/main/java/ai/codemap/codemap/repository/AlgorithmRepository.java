package ai.codemap.codemap.repository;

import ai.codemap.codemap.dto.SimpleAlgorithmDto;
import ai.codemap.codemap.model.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlgorithmRepository {

    private final EntityManager em;

    public Algorithm findById(Long algorithmId) {
        return em.find(Algorithm.class, algorithmId);
    }
    public List<Algorithm> findAll() {
        return em.createQuery("select a from Algorithm a", Algorithm.class)
                .getResultList();
    }

    public Long save(Algorithm algorithm) {
        em.persist(algorithm);
        return algorithm.getAlgorithmId();
    }

    public List<SimpleAlgorithmDto> getSimpleList() {
        return em.createQuery("select new ai.codemap.codemap.dto.SimpleAlgorithmDto(a.algorithmId, a.title, a.description) from Algorithm a", SimpleAlgorithmDto.class)
                .getResultList();
    }
}
