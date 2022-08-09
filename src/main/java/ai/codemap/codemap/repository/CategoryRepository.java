package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public Category findById(Long categoryId) {
        return em.find(Category.class, categoryId);
    }

    public Long save(Category category) {
        em.persist(category);
        return category.getCategoryId();
    }

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
