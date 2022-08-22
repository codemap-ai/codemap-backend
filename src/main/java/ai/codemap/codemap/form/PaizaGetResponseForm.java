package ai.codemap.codemap.form;

import lombok.Data;

@Data
public class PaizaGetResponseForm {
    private String id;
    private String language;
    private String note;
    private String status;
    private String build_stdout;
    private String build_stderr;
    private int build_exit_code;
    private String build_time;
    private int build_memory;
    private String build_result;
    private String stdout;
    private String stderr;
    private int exit_code;
    private String time;
    private int memory;
    private int connections;
    private String result;

}
