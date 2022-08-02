package ai.codemap.codemap.controller2;

import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.service.AlgorithmService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    @GetMapping("/admin/algorithms")
    public String getAlgorithms(Model model) {
        List<Algorithm> algorithms = algorithmService.getAll();
        model.addAttribute("algorithms", algorithms);
        return "algorithms";
    }

    @GetMapping("/admin/algorithm/{algorithmId}")
    public String getAlgorithm(@PathVariable Long algorithmId, Model model) {
        Algorithm algorithm = algorithmService.getOne(algorithmId);
        AlgorithmForm algorithmForm = new AlgorithmForm(algorithmId, algorithm.getTitle(), algorithm.getDescription());
        model.addAttribute("algorithmForm", algorithmForm);
        model.addAttribute("algorithmId", algorithmId);
        return "algorithm";
    }

    @PostMapping("/admin/algorithm/{algorithmId}")
    public String updateAlgorithm(@PathVariable Long algorithmId, AlgorithmForm algorithmForm) {
        algorithmService.update(algorithmId, algorithmForm.getTitle(), algorithmForm.getDescription());
        return "redirect:/admin/algorithm" + algorithmId;
    }

    @PostMapping("/admin/algorithm/{algorithmId}/content")
    public String updateContent(@PathVariable Long algorithmId, @RequestParam List<MultipartFile> files) {
        String json = "";
        try {
            json = algorithmService.serialize(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: Save to database
        return "redirect:/admin/algorithm" + algorithmId;
    }

    @PostMapping("/admin/algorithms/create")
    public String createAlgorithm() {
        Long id = algorithmService.createAlgorithm();
        return "redirect:/admin/algorithm/" + id;
    }

    @Data @AllArgsConstructor
    static class AlgorithmForm {

        Long algorithmId;

        @NotBlank
        String title;

        String description;
    }
}
