package ai.codemap.codemap.controller;

import ai.codemap.codemap.dto.SimpleAlgorithmDto;
import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.service.AlgorithmService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlgorithmRestController {
    private final AlgorithmService algorithmService;

    @GetMapping("/algorithms")
    public List<SimpleAlgorithmDto> getAlgorithmList() {
        return algorithmService.getSimpleList();
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
        private String body;
        private String description;
    }
}
