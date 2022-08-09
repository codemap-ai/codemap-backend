package ai.codemap.codemap.service;

import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.model.Category;
import ai.codemap.codemap.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(String title, String description) {
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    public List<Algorithm> getAlgorithms(Long categoryId) {
        Category category = categoryRepository.findById(categoryId);
        return category.getAlgorithms();
    }
}
