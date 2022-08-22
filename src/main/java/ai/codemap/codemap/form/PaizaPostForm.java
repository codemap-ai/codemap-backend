package ai.codemap.codemap.form;

import lombok.Data;

@Data
public class PaizaPostForm {
    private String source_code;
    private String language;
    private String input;
    private boolean longpoll;
    private double longpoll_timeout;
    private String api_key;
}
