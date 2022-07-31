package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.ResponseForm;
import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.service.AlgorithmService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/algorithms")
public class AlgorithmRestController {
    private final AlgorithmService algorithmService;

    @Autowired
    public AlgorithmRestController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @GetMapping("")
    public ResponseEntity getAlgorithmList() {

        List<Algorithm> list = algorithmService.getALL();
        if (list == null) list = new ArrayList<Algorithm>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{algorithm_id}")
    public ResponseEntity getAlgorithm(@PathVariable String algorithm_id) {
        Algorithm algorithm = algorithmService.getOne(Integer.parseInt(algorithm_id));


        if (algorithm == null) {
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(algorithm);
    }
}
