package ai.codemap.codemap.controller;

import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.service.SubmissionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit")
public class SubmitRestController {

    private final SubmissionService submissionService;
    private static final String EXCHANGE_NAME = "judge.exchange";
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    public SubmitRestController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }


    @PostMapping("/submission")
    public ResponseEntity<Submission> get_submission(@RequestBody Submission submission){

        submissionService.addSubmission(submission);
        return ResponseEntity.ok(submission);
    }

    @PostMapping("")
    public ResponseEntity<SubmitForm> submit(@RequestBody SubmitForm submitForm){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "4242", submitForm);

        return ResponseEntity.ok(submitForm);
    }

    @GetMapping("/test")
    public String testQ(){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "4242", "RabbitMQ + Springboot = Success!");
        return "messeage send";
    }

}
