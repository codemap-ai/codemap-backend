package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.ResponseForm;
import ai.codemap.codemap.model.Algorithm;
import ai.codemap.codemap.service.AlgorithmService;
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
    public ResponseForm getAlgorithmList() {

        List<Algorithm> list = algorithmService.getAll();
        if (list == null) list = new ArrayList<>();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(list));
        return responseForm;
    }

    @GetMapping("/{algorithm_id}")
    public ResponseForm getAgorithm(@PathVariable String algorithm_id) {
        Algorithm algorithm = algorithmService.getOne(Long.parseLong(algorithm_id));
        ResponseForm responseForm = new ResponseForm();

        if (algorithm == null) {
            responseForm.setResponseEntity(ResponseEntity.badRequest().build());
            return responseForm;
        }
        responseForm.setResponseEntity(ResponseEntity.ok(algorithm));
        return responseForm;
    }
}
