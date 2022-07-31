package ai.codemap.codemap.controller.admin;

import ai.codemap.codemap.model.ProblemResource;
import ai.codemap.codemap.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemResourceController {

    private final S3Service s3Service;

    @GetMapping("/admin/src/{problem_id}")
    public String listUploadedFiles(@PathVariable("problem_id") Long problemId, Model model) {
        // TODO: Validate whether corresponding problem-id exists or not
        List<ProblemResource> list = s3Service.listFiles(problemId);
        model.addAttribute("problemId", problemId);
        model.addAttribute("resources", list);
        return "resources";
    }

    @PostMapping("/admin/src/{problem_id}/delete")
    public String deleteFile(@PathVariable("problem_id") Long problemId, @RequestParam("id") Long problemResourceId, @RequestParam("objectName") String objectName, RedirectAttributes redirectAttributes) {
        s3Service.deleteFile(problemResourceId, objectName);
        redirectAttributes.addFlashAttribute("message", "Successfully deleted");
        return "redirect:/admin/src/" + problemId;
    }

    @PostMapping("/admin/src/{problem_id}")
    public String upload(@PathVariable("problem_id") Long problemId, @RequestParam("files") List<MultipartFile> files, RedirectAttributes redirectAttributes) {
        s3Service.uploadFiles(problemId, files);
        redirectAttributes.addFlashAttribute("message", "Successfully uploaded!");
        return "redirect:/admin/src/" + problemId;
    }
}
