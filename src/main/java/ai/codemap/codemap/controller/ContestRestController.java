package ai.codemap.codemap.controller;

import ai.codemap.codemap.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contest")
public class ContestRestController {
    private final ContestService contestService;

    @Autowired
    public ContestRestController(ContestService contestService) {
        this.contestService = contestService;
    }
}
