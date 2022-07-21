package ai.codemap.codemap.controller;

import ai.codemap.codemap.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRestController {
    private final TestService testService;

    @Autowired
    public TestRestController(TestService testService) {
        this.testService = testService;
    }
}
