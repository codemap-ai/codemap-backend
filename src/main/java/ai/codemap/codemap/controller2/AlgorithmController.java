package ai.codemap.codemap.controller2;

import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.model.Category;
import ai.codemap.codemap.service.AlgorithmService;
import ai.codemap.codemap.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class AlgorithmController {

    private final AlgorithmService algorithmService;
    private final CategoryService categoryService;

    @Autowired
    public AlgorithmController(AlgorithmService algorithmService, CategoryService categoryService) {
        this.algorithmService = algorithmService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/algorithms")
    public String getAlgorithms(Model model) {
        List<Algorithm> algorithms = algorithmService.getAll();
        model.addAttribute("algorithms", algorithms);
        return "algorithms";
    }

    @GetMapping("/admin/algorithm/{algorithmId}")
    public String getAlgorithm(@PathVariable Long algorithmId, Model model) {
        Algorithm algorithm = algorithmService.getOne(algorithmId);
        Long categoryId = null;
        if (algorithm.getCategory() != null)
            categoryId = algorithm.getCategory().getCategoryId();
        AlgorithmForm algorithmForm = new AlgorithmForm(algorithmId, categoryId, algorithm.getTitle(), algorithm.getDescription());

        List<Category> categories = categoryService.getAll();

        model.addAttribute("algorithmForm", algorithmForm);
        model.addAttribute("algorithmId", algorithmId);
        model.addAttribute("json", algorithm.getBody());
        model.addAttribute("categories", categories);

        return "algorithm";
    }

    @PostMapping("/admin/algorithm/{algorithmId}")
    public String updateAlgorithm(@PathVariable Long algorithmId, @Valid AlgorithmForm algorithmForm) {
        algorithmService.update(algorithmId, algorithmForm.getCategoryId(), algorithmForm.getTitle(), algorithmForm.getDescription());
        return "redirect:/admin/algorithm/" + algorithmId;
    }

    @PostMapping("/admin/algorithm/{algorithmId}/content")
    public String updateContent(@PathVariable Long algorithmId, @RequestParam List<MultipartFile> files) {
        String json = "";
        try {
            json = algorithmService.serialize(files);
        } catch (IOException e) {
            log.warn("Failed to serialize algorithm contents.", e);
        }
        algorithmService.update(algorithmId, json);
        return "redirect:/admin/algorithm/" + algorithmId;
    }

    @PostMapping("/admin/algorithms/create")
    public String createAlgorithm() {
        Long id = algorithmService.createAlgorithm();
        return "redirect:/admin/algorithm/" + id;
    }

    @Data @AllArgsConstructor
    static class AlgorithmForm {

        Long algorithmId;
        Long categoryId;
        @NotBlank String title;
        String description;
    }
}
