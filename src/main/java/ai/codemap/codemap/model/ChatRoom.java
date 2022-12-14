package ai.codemap.codemap.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션

    public static ChatRoom create(String name){
        ChatRoom room = new ChatRoom();

        room.roomId = name;
        room.name = name;
        return room;
    }
}
