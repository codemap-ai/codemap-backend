package ai.codemap.codemap.controller2;

import ai.codemap.codemap.model.Category;
import ai.codemap.codemap.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        List<Category> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @PostMapping("/admin/categories/create")
    public String createCategory() {
        categoryService.createCategory();
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories")
    public String updateCategory(@RequestParam Long categoryId, @RequestParam String title, @RequestParam String description) {
        categoryService.updateCategory(categoryId, title, description);
        return "redirect:/admin/categories";
    }
}
