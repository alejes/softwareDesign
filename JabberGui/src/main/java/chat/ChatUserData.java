package chat;


import java.util.LinkedList;

/**
 * Data associated wit one chat user
 */
public class ChatUserData {
    public final LinkedList<ChatMessage> messages = new LinkedList<>();
    public String name = "Unknown";
    public Integer port = 0;
    public String status = "";

    public ChatUserData() {}

    public ChatUserData(String name, Integer port, String status) {
        this.name = name;
        this.port = port;
        this.status = status;
    }
}


