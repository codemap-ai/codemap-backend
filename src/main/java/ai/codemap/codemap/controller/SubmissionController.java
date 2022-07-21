package ai.codemap.codemap.controller;

import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Submission>> getSubmissionList() {
        List<Submission> list = submissionService.getAll();
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/test/{test_id}")
    public ResponseEntity<List<Submission>> getSubmissionListByTestId(@PathVariable String test_id) {
        List<Submission> list = submissionService.getByTestId(Integer.parseInt(test_id));
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Submission>> getSubmissionListByUserId(@PathVariable String user_id) {
        List<Submission> list = submissionService.getByUserId(Integer.parseInt(user_id));
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/problem/{problem_id}")
    public ResponseEntity<List<Submission>> getSubmissionListByProblemId(@PathVariable String problem_id) {
        List<Submission> list = submissionService.getByProblemId(Integer.parseInt(problem_id));
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{submission_id}")
    public ResponseEntity<Submission> getSubmission(@PathVariable String submission_id) {
        Submission submission = submissionService.getOne(Integer.parseInt(submission_id));
        if (submission == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(submission);
    }
}
