package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String roomId;
    private String message;
}
