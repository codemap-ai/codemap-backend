package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.*;
import ai.codemap.codemap.model.Submission;
import ai.codemap.codemap.repository.ChatRoomRepository;
import ai.codemap.codemap.service.SubmissionService;
import ai.codemap.codemap.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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


    @PostMapping("/test")
    public ResponseEntity testCode(@RequestBody TestCodeForm testCodeForm) throws Exception {
        URL url = new URL("http://api.paiza.io:80/runners/create");

        ObjectMapper mapper = new ObjectMapper();


        PaizaPostForm paizaPostForm = new PaizaPostForm();
        paizaPostForm.setSource_code(testCodeForm.getSource());
        paizaPostForm.setInput(testCodeForm.getInput());
        paizaPostForm.setLanguage(testCodeForm.getLanguage());
        paizaPostForm.setLongpoll(true);
        paizaPostForm.setLongpoll_timeout(3);
        paizaPostForm.setApi_key("guest");

        HttpURLConnection postConn = (HttpURLConnection) url.openConnection();
        postConn.setDoOutput(true);
        postConn.setRequestMethod("POST");
        postConn.setRequestProperty("Content-Type", "application/json; utf-8");
        postConn.setRequestProperty("Accept", "application/json");

        String message = mapper.writeValueAsString(paizaPostForm);
        PaizaPostResponseForm paizaPostResponseForm;

        try (OutputStream os = postConn.getOutputStream()) {
            byte[] input = message.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(postConn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            paizaPostResponseForm = mapper.readValue(response.toString(), PaizaPostResponseForm.class);
        }

        HttpURLConnection getConn = null;
        String getURL = "http://api.paiza.io:80/runners/get_status?id=" + paizaPostResponseForm.getId() + "&api_key=guest";
        url = new URL(getURL);

        while (true) {
            try {
                getConn = (HttpURLConnection) url.openConnection();
                getConn.setRequestMethod("GET");
                getConn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");


            } catch (Exception e) {
                e.printStackTrace();
            }

            GetStatusForm getStatusForm;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(getConn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                getStatusForm = mapper.readValue(response.toString(), GetStatusForm.class);
            }

            if(getStatusForm.getStatus().equals("completed")) break;
        }


        getConn = null;
        getURL = "http://api.paiza.io:80/runners/get_details?id=" + paizaPostResponseForm.getId() + "&api_key=guest";
        url = new URL(getURL);


        try {
            getConn = (HttpURLConnection) url.openConnection();
            getConn.setRequestMethod("GET");
            getConn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");


        } catch (Exception e) {
            e.printStackTrace();
        }

        PaizaGetResponseForm paizaGetResponseForm;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getConn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            paizaGetResponseForm = mapper.readValue(response.toString(), PaizaGetResponseForm.class);
        }

        System.out.println(paizaGetResponseForm);

        if(paizaGetResponseForm.getResult()!=null && paizaGetResponseForm.getResult().equals("timeout")) ResponseEntity.ok("timeout");

        String responseMessage = paizaGetResponseForm.getBuild_stderr();
        if (responseMessage == "") responseMessage = paizaGetResponseForm.getStdout();



        return ResponseEntity.ok(responseMessage);
    }

}


