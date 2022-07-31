package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.ResponseForm;
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
public class SubmissionRestController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionRestController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping("/all")
    public ResponseEntity getSubmissionList() {
        List<Submission> list = submissionService.getAll();
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/contest/{contest_id}")
    public ResponseEntity getSubmissionListByContestId(@PathVariable String contest_id) {
        List<Submission> list = submissionService.getByContestId(Integer.parseInt(contest_id));
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }


    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Submission>> getSubmissionListByUserId(@PathVariable String user_id) {
        List<Submission> list = submissionService.getByUserId(Long.parseLong(user_id));
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/problem/{problem_id}")
    public ResponseEntity getSubmissionListByProblemId(@PathVariable String problem_id) {
        List<Submission> list = submissionService.getByProblemId(Integer.parseInt(problem_id));
        if (list == null) list = new ArrayList<Submission>();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/id/{submission_id}")
    public ResponseEntity getSubmission(@PathVariable String submission_id) {
        Submission submission = submissionService.getOne(Long.parseLong(submission_id));
        ResponseForm responseForm = new ResponseForm();
        if (submission == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(submission);
    }
}
