package ai.codemap.codemap.controller.admin;

import ai.codemap.codemap.form.IOFileForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class FileUploadController {

    @GetMapping("/admin")
    public String listUploadedFiles(Model model) throws IOException {
        var files = Files.walk(Paths.get("/Users/hyunjae/Desktop/tmp"))
                .filter(Files::isRegularFile)
                .sorted()
                .map(path -> path.getFileName().toString())
                .toList();
        model.addAttribute("files", files);
        return "uploadForm";
    }

    @PostMapping("/admin/delete")
    public String deleteFile(IOFileForm ioFileForm, RedirectAttributes redirectAttributes) throws IOException {
        Files.deleteIfExists(Paths.get("/Users/hyunjae/Desktop/tmp").resolve(ioFileForm.getFilename()));
        redirectAttributes.addFlashAttribute("message", "You successfully deleted " + ioFileForm.getFilename() + "!");
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        file.transferTo(Paths.get("/Users/hyunjae/Desktop/tmp").resolve(file.getOriginalFilename()));
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/admin";
    }
}
