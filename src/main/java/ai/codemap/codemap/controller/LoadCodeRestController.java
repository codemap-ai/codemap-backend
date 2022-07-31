package ai.codemap.codemap.controller;

import ai.codemap.codemap.form.LoadCodeForm;
import ai.codemap.codemap.form.ResponseForm;
import ai.codemap.codemap.form.SaveCodeForm;
import ai.codemap.codemap.model.LoadCode;
import ai.codemap.codemap.repository.LoadCodeRepository;
import ai.codemap.codemap.service.LoadCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class LoadCodeRestController {
    private final LoadCodeService loadCodeService;

    public LoadCodeRestController(LoadCodeService loadCodeService) {
        this.loadCodeService = loadCodeService;
    }

    @PostMapping("/load")
    public ResponseEntity getCode(@RequestBody LoadCodeForm loadCodeForm) {
        LoadCode loadCode = loadCodeService.getByContestIdAndProblemId(loadCodeForm.getContestId(), loadCodeForm.getProblemId(), loadCodeForm.getLanguage());


        if (loadCode == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(loadCode.getCode());
    }

    @PostMapping("/save")
    public ResponseEntity addCode(@RequestBody SaveCodeForm saveCodeForm) {
        LoadCode loadCode = loadCodeService.getByContestIdAndProblemId(saveCodeForm.getContestId(), saveCodeForm.getProblemId(), saveCodeForm.getLanguage());

        if (loadCode == null) {
            loadCode = new LoadCode();
            loadCode.setContestId(saveCodeForm.getContestId());
            loadCode.setProblemId(saveCodeForm.getProblemId());
            loadCode.setLanguage(saveCodeForm.getLanguage());
        }
        loadCode.setCode(saveCodeForm.getCode());
        loadCodeService.addLoadCode(loadCode);
        return ResponseEntity.ok(loadCode);
    }

}
