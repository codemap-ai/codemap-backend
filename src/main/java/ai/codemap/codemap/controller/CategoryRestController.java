package ai.codemap.codemap.controller;

import ai.codemap.codemap.model.Category;
import ai.codemap.codemap.service.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/algorithms/category")
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryService.getAll();
        List<CategoryResponse> result = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponse response = new CategoryResponse();
            response.setCategoryId(category.getCategoryId());
            response.setTitle(category.getTitle());
            response.setDescription(category.getDescription());
            result.add(response);
        }
        return result;
    }

    @Data
    static class CategoryResponse {

        private Long categoryId;
        private String title;
        private String description;
    }
}
