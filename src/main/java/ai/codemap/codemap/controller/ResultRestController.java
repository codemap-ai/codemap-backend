package ai.codemap.codemap.controller;

import ai.codemap.codemap.model.Problem;
import ai.codemap.codemap.model.Result;
import ai.codemap.codemap.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultRestController {
    private final ResultService resultService;

    @Autowired
    public ResultRestController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("")
    public ResponseEntity<List<Result>> getResultList() {
        List<Result> list = resultService.getAll();
        if (list == null) list = new ArrayList<Result>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{result_id}")
    public ResponseEntity<Result> getResult(@PathVariable String result_id) {
        Result result = resultService.getOne(Integer.parseInt(result_id));

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/problem/{problem_id}")
    public ResponseEntity<List<Result>> getResultListByPid(@PathVariable String problem_id) {
        List<Result> list = resultService.getByPid(Integer.parseInt(problem_id));
        if (list == null) list = new ArrayList<Result>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Result>> getResultListByUid(@PathVariable String user_id) {
        List<Result> list = resultService.getByUid(Integer.parseInt(user_id));
        if (list == null) list = new ArrayList<Result>();

        return ResponseEntity.ok(list);
    }
}
