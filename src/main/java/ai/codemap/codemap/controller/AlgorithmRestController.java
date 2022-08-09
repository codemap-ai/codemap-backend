package ai.codemap.codemap.controller;

import ai.codemap.codemap.dto.SimpleAlgorithmDto;
import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.service.AlgorithmService;
import ai.codemap.codemap.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlgorithmRestController {
    private final AlgorithmService algorithmService;
    private final CategoryService categoryService;

    @Autowired
    public AlgorithmRestController(AlgorithmService algorithmService, CategoryService categoryService) {
        this.algorithmService = algorithmService;
        this.categoryService = categoryService;
    }

    @GetMapping("/algorithms")
    public List<SimpleAlgorithmDto> getAlgorithmList() {
        return algorithmService.getSimpleList();
    }

    @GetMapping("/algorithms/category/{categoryId}")
    public List<SimpleAlgorithmDto> getAlgorithmList(@PathVariable Long categoryId) {
        List<Algorithm> algorithms = categoryService.getAlgorithms(categoryId);
        List<SimpleAlgorithmDto> result = new ArrayList<>();
        for (Algorithm algorithm : algorithms) {
            result.add(new SimpleAlgorithmDto(algorithm.getAlgorithmId(), algorithm.getTitle(), algorithm.getDescription()));
        }
        return result;
    }

    @GetMapping("/algorithms/{algorithmId}")
    public AlgorithmResponse getAlgorithm(@PathVariable Long algorithmId) {
        Algorithm algorithm = algorithmService.getOne(algorithmId);

        AlgorithmResponse response = new AlgorithmResponse();
        response.setAlgorithmId(algorithm.getAlgorithmId());
        response.setTitle(algorithm.getTitle());
        response.setBody(algorithm.getBody());
        response.setDescription(algorithm.getDescription());

        return response;
    }



    @Data
    static class AlgorithmResponse {
        private Long algorithmId;
        private String title;
        @JsonRawValue
        private String body;
        private String description;
    }
}
