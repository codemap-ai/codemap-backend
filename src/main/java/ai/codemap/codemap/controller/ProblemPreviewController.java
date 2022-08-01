package ai.codemap.codemap.controller;

import ai.codemap.codemap.dto.ExampleDto;
import ai.codemap.codemap.model.ProblemResource;
import ai.codemap.codemap.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static ai.codemap.codemap.model.ProblemResource.Type.INPUT;
import static ai.codemap.codemap.model.ProblemResource.Type.OUTPUT;

@Controller
public class ProblemPreviewController {

    private final S3Service s3Service;

    @Autowired
    public ProblemPreviewController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/admin/preview/{problemId}")
    public String getPreview(@PathVariable Long problemId, Model model) {
        List<ExampleDto> examples = new ArrayList<>();
        List<String> inputs = s3Service.getExamples(problemId, INPUT);
        List<String> outputs = s3Service.getExamples(problemId, OUTPUT);
        int max = Math.max(inputs.size(), outputs.size());
        for (int i = 0; i < max; i++) {
            examples.add(new ExampleDto());
        }
        for (int i = 0; i < inputs.size(); i++) {
            examples.get(i).setInput(inputs.get(i));
        }
        for (int i = 0; i < outputs.size(); i++) {
            examples.get(i).setOutput(outputs.get(i));
        }
        model.addAttribute("problemId", problemId);
        model.addAttribute("examples", examples);
        return "problem_preview";
    }
}
