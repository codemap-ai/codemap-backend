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
    public ResponseForm getSubmissionList() {
        List<Submission> list = submissionService.getAll();
        if (list == null) list = new ArrayList<Submission>();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(list));
        return responseForm;
    }

    @GetMapping("/contest/{contest_id}")
    public ResponseForm getSubmissionListByContestId(@PathVariable String contest_id) {
        List<Submission> list = submissionService.getByContestId(Integer.parseInt(contest_id));
        if (list == null) list = new ArrayList<Submission>();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(list));
        return responseForm;
    }


    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Submission>> getSubmissionListByUserId(@PathVariable String user_id) {
        List<Submission> list = submissionService.getByUserId(Integer.parseInt(user_id));
        if (list == null) list = new ArrayList<Submission>();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(list));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/problem/{problem_id}")
    public ResponseForm getSubmissionListByProblemId(@PathVariable String problem_id) {
        List<Submission> list = submissionService.getByProblemId(Integer.parseInt(problem_id));
        if (list == null) list = new ArrayList<Submission>();

        ResponseForm responseForm = new ResponseForm();
        responseForm.setResponseEntity(ResponseEntity.ok(list));
        return responseForm;
    }

    @GetMapping("/id/{submission_id}")
    public ResponseForm getSubmission(@PathVariable String submission_id) {
        Submission submission = submissionService.getOne(Long.parseLong(submission_id));
        ResponseForm responseForm = new ResponseForm();
        if (submission == null) {
            responseForm.setResponseEntity(ResponseEntity.badRequest().build());
            return responseForm;
        }
        responseForm.setResponseEntity(ResponseEntity.ok(submission));
        return responseForm;
    }
}
