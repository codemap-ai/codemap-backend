package ai.codemap.codemap.controller;

import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.service.SubmissionService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit")
public class SubmitRestController {
    private final String[] _language = {"c++14", "c++17","c++20","python3","java17"};
    private final SubmissionService submissionService;
    private static final String QUEUE_NAME = "judge-queue";
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    @Autowired
    public SubmitRestController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }


    @PostMapping("/submission")
    public ResponseEntity<JudgeToMain> get_submission(@RequestBody JudgeToMain judgeToMain){


        return ResponseEntity.ok(judgeToMain);
    }

    @PostMapping("")
    public int submit(@RequestBody SubmitForm submitForm){
        MainToJudge mainToJudge = new MainToJudge();
        Submission submission = new Submission();

        final int submissionId = submissionService.addSubmission(submission).getSubmissionId();

        mainToJudge.setId(submissionId);
        mainToJudge.setLanguage(_language[submitForm.getLanguage()]);
        mainToJudge.setProblemId(submitForm.getProblem_id());
        mainToJudge.setSource(submitForm.getSource());

        rabbitTemplate.convertAndSend(queue.getName(), mainToJudge);

        return submissionId;
    }


    @GetMapping("/test")
    public String testQ(){
        //submissionService.setUpdate(1, 4);//
        return "messeage send";
    }

}
