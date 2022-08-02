package ai.codemap.codemap.service;

import ai.codemap.codemap.repository.AlgorithmRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ai.codemap.codemap.model.Algorithm;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class AlgorithmService {
    private final AlgorithmRepository algorithmRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AlgorithmService(AlgorithmRepository algorithmRepository, ObjectMapper objectMapper) {
        this.algorithmRepository = algorithmRepository;
        this.objectMapper = objectMapper;
    }

    public Long createAlgorithm() {
        Algorithm algorithm = new Algorithm();
        algorithm.setTitle("New Algorithm");
        algorithm.setDescription("Algorithm Description");
        algorithm.setBody("");
        return algorithmRepository.save(algorithm);
    }

    public void update(Long algorithmId, String title, String description) {
        Algorithm algorithm = algorithmRepository.findById(algorithmId);
        algorithm.setTitle(title);
        algorithm.setDescription(description);
    }

    public Algorithm getOne(Long algorithmId){
            return algorithmRepository.findById(algorithmId);
        }
    public List<Algorithm> getAll(){
        return algorithmRepository.findAll();
    }

    public String serialize(List<MultipartFile> files) throws IOException {
        List<Page> list = new ArrayList<>();
        for (MultipartFile file : files) {
            StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
            String title = st.nextToken();
            int chapter = Integer.parseInt(st.nextToken());
            int row = Integer.parseInt(st.nextToken());
            int column = Integer.parseInt(st.nextToken());

            String body = new String(file.getInputStream().readAllBytes());

            Page page = new Page();
            page.setTitle(title);
            page.setChapter(chapter);
            page.setRow(row);
            page.setColumn(column);
            page.setBody(body);

            list.add(page);
        }

        Collections.sort(list);

        List<Chapter> chapters = new ArrayList<>();
        for (Page page : list) {
            if (page.getChapter() > chapters.size()) {
                chapters.add(new Chapter(page.getTitle()));
            }
            Chapter chapter = chapters.get(page.getChapter() - 1);
            if (page.getRow() > chapter.getPages().size()) {
                chapter.getPages().add(new ArrayList<>());
            }
            chapter.getPages().get(page.getRow() - 1).add(page.getBody());
        }

        return objectMapper.writeValueAsString(chapters);
    }

    @Getter @Setter
    static class Chapter {

        private String title;
        private List<List<String>> pages = new ArrayList<>();

        Chapter (String title) {
            this.title = title;
        }
    }

    @Getter @Setter
    static class Page implements Comparable<Page> {

        private String title;
        private int chapter;
        private int row;
        private int column;

        private String body;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Page page = (Page) o;
            return getChapter() == page.getChapter() && getRow() == page.getRow() && getColumn() == page.getColumn() && Objects.equals(getTitle(), page.getTitle()) && Objects.equals(getBody(), page.getBody());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTitle(), getChapter(), getRow(), getColumn(), getBody());
        }

        @Override
        public int compareTo(Page o) {
            if (this.chapter != o.chapter) {
                return Integer.compare(this.chapter, o.chapter);
            }
            if (this.row != o.row) {
                return Integer.compare(this.row, o.row);
            }
            return Integer.compare(this.column, o.column);
        }
    }
}
