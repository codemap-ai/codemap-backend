package ai.codemap.codemap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class CodemapApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CodemapApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
