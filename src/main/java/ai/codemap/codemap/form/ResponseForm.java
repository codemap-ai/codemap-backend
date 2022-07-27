package ai.codemap.codemap.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
@Data
@Getter
@Setter
public class ResponseForm {
    ResponseEntity<?> responseEntity;
}
