package ai.codemap.codemap.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SubmitForm {
    private int sid;
    private int uid;
    private int pid;
    private int language;
    private String code;
}
