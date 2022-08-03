package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.JudgeToMain;
import ai.codemap.codemap.form.MainToJudge;
import ai.codemap.codemap.form.ResponseForm;
import ai.codemap.codemap.form.SubmitForm;
import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.repository.ChatRoomRepository;
import ai.codemap.codemap.service.SubmissionService;
import ai.codemap.codemap.service.UserService;
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
    private final UserService userService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    @Autowired
    public SubmitRestController(SubmissionService submissionService, SimpMessageSendingOperations sendingOperations, ChatRoomRepository chatRoomRepository, UserService userService) {
        this.submissionService = submissionService;
        this.sendingOperations = sendingOperations;
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
    }


    @PostMapping("/submission")
    public ResponseEntity get_submission(@RequestBody JudgeToMain judgeToMain) {

        Submission submission = submissionService.getOne(judgeToMain.getSubmissionId());
        submission.setExecuteTime(judgeToMain.getTime());
        submission.setUsedMemory(judgeToMain.getMemory());
        submission.setResult(judgeToMain.getStatus());
        submission.setScore(judgeToMain.getScore());
        submission.setCompilerMessage(judgeToMain.getCompilerMessage());
        submission.setOutput(judgeToMain.getOutput());

        submissionService.addSubmission(submission);

//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setRoomId(toString(judgeToMain.getSubmissionId()));
//        chatMessage.setMessage("FINISH");

        sendingOperations.convertAndSend("/topic/chat/room/" + judgeToMain.getSubmissionId(), submission);


        return ResponseEntity.ok(judgeToMain);
    }

    private String toString(int submissionId) {
        return Integer.toString(submissionId);
    }

    @PostMapping("")
    public ResponseEntity submit(@RequestBody SubmitForm submitForm) {
        MainToJudge mainToJudge = new MainToJudge();
        Submission submission = new Submission();

        submission.setUserId(userService.getCurrentUserId()); // todo

        submission.setProblemId(submitForm.getProblemId());
        submission.setContestId(submitForm.getContestId());
        submission.setUsedLanguage(submitForm.getLanguage());
        submission.setSubmitCode(submitForm.getSource());
        submission.setSubmitDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        submission.setResult("WAITING");
        submission.setTestMode(submitForm.getTestMode());
        submission.setInput(submitForm.getInput());
        final Long submissionId = submissionService.addSubmission(submission).getSubmissionId();

        chatRoomRepository.createRoom(submissionId.toString());

        mainToJudge.setId(submissionId);
        mainToJudge.setLanguage(submitForm.getLanguage());
        mainToJudge.setProblemId(submitForm.getProblemId());
        mainToJudge.setSource(submitForm.getSource());
        mainToJudge.setInput(submitForm.getInput());
        mainToJudge.setTestMode(submitForm.getTestMode());

        rabbitTemplate.convertAndSend(queue.getName(), mainToJudge);


        return ResponseEntity.ok(submission);
    }


    @GetMapping("/test")
    public String testQ() {
        Submission submission = submissionService.getOne(1L);
        submission.setProblemId(4);
        submissionService.addSubmission(submission);
        return "messeage send";
    }
}
