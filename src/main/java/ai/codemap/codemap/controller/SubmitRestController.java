package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.JudgeToMain;
import ai.codemap.codemap.form.MainToJudge;
import ai.codemap.codemap.form.SubmitForm;
import ai.codemap.codemap.model.ChatMessage;
import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.repository.ChatRoomRepository;
import ai.codemap.codemap.service.SubmissionService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

@RestController
@RequestMapping("/submit")
public class SubmitRestController {
    private final SubmissionService submissionService;
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatRoomRepository chatRoomRepository;


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    @Autowired
    public SubmitRestController(SubmissionService submissionService, SimpMessageSendingOperations sendingOperations, ChatRoomRepository chatRoomRepository) {
        this.submissionService = submissionService;
        this.sendingOperations = sendingOperations;
        this.chatRoomRepository = chatRoomRepository;
    }


    @PostMapping("/submission")
    public ResponseEntity<JudgeToMain> get_submission(@RequestBody JudgeToMain judgeToMain) {

        Submission submission = submissionService.getOne(judgeToMain.getSubmissionId());
        submission.setExecuteTime(judgeToMain.getTime());
        submission.setUsedMemory(judgeToMain.getMemory());
        submission.setResult(judgeToMain.getStatus());
        submission.setScore(judgeToMain.getScore());
        submission.setCompilerMessage(judgeToMain.getCompilerMessage());

        submissionService.addSubmission(submission);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(toString(judgeToMain.getSubmissionId()));
        chatMessage.setMessage("FINISH");

        sendingOperations.convertAndSend("/topic/chat/room/"+toString(judgeToMain.getSubmissionId()), chatMessage);

        return ResponseEntity.ok(judgeToMain);
    }

    private String toString(int submissionId) {
        return toString(submissionId);
    }

    @PostMapping("")
    public int submit(@RequestBody SubmitForm submitForm) {
        MainToJudge mainToJudge = new MainToJudge();
        Submission submission = new Submission();

        submission.setUserId(submitForm.getUserId());
        submission.setProblemId(submitForm.getProblemId());
        submission.setContestId(submitForm.getContestId());
        submission.setUsedLanguage(submitForm.getLanguage());
        submission.setSubmitCode(submitForm.getSource());
        submission.setSubmitDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        submission.setResult("Waiting");
        final int submissionId = submissionService.addSubmission(submission).getSubmissionId();

        chatRoomRepository.createRoom(toString(submissionId));

        mainToJudge.setId(submissionId);
        mainToJudge.setLanguage(submitForm.getLanguage());
        mainToJudge.setProblemId(submitForm.getProblemId());
        mainToJudge.setSource(submitForm.getSource());

        //rabbitTemplate.convertAndSend(queue.getName(), mainToJudge);

        return submissionId;
    }


    @GetMapping("/test")
    public String testQ() {
        Submission submission = submissionService.getOne(1);
        submission.setProblemId(4);
        submissionService.addSubmission(submission);
        return "messeage send";
    }
}
