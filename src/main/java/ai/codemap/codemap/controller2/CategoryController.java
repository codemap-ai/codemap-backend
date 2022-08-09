package ai.codemap.codemap.controller2;

import ai.codemap.codemap.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        
    }
}
