package ai.codemap.codemap.service;

import ai.codemap.codemap.model.ProblemResource;
import ai.codemap.codemap.repository.ProblemResourceRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final ProblemResourceRepository repository;

    @Autowired
    public S3Service(AmazonS3 amazonS3, ProblemResourceRepository repository) {
        this.amazonS3 = amazonS3;
        this.repository = repository;
    }

    public List<ProblemResource> listFiles(Long problemId) {
        return repository.findByProblemId(problemId);
    }
    public void uploadFiles(Long problemId, List<MultipartFile> multipartFiles) {
        multipartFiles.forEach(file -> {

            if (file.getSize() == 0) return;

            String key = problemId + "-" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(bucket, key, inputStream, objectMetadata);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload files");
            }

            addProblemResource(problemId, file.getOriginalFilename(), key);
        });
    }

    private void addProblemResource(Long problemId, String name, String key) {
        ProblemResource problemResource = new ProblemResource();
        problemResource.setProblemId(problemId);
        problemResource.setName(name);
        problemResource.setObjectName(key);

        ProblemResource.Type type = ProblemResource.Type.UNKNOWN;
        if (name.endsWith(".in") || name.endsWith(".in.txt")) {
            type = ProblemResource.Type.INPUT;
        } else if (name.endsWith(".out") || name.endsWith(".ans") || name.endsWith(".out.txt")) {
            type = ProblemResource.Type.OUTPUT;
        }

        problemResource.setType(type);

        repository.save(problemResource);
    }

    public void deleteFile(Long problemResourceId, String objectName) {
        amazonS3.deleteObject(bucket, objectName);
        repository.remove(problemResourceId);
    }

    public void toggleExample(Long problemResourceId) {
        ProblemResource resource = repository.findOne(problemResourceId);
        resource.setExample(!resource.isExample());
    }

    public List<String> getExamples(Long problemId, ProblemResource.Type type) {
        return repository.findExamples(problemId, type).stream()
                .map(problemResource -> amazonS3.getObjectAsString(bucket, problemResource.getObjectName()))
                .toList();
    }
}
