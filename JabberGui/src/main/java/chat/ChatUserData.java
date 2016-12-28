package chat;


import java.util.LinkedList;

/**
 * Data associated wit one chat user
 */
public class ChatUserData {
    public final LinkedList<ChatMessage> messages = new LinkedList<>();
    /**
     * current name of this user
     */
    public String name = "Unknown";
    /**
     * current status of this user
     */
    public String status = "";
    /**
     * port that listen this client
     */
    public Integer port = 0;

    /**
     * create user data with default parameters
     */
    public ChatUserData() {}

    /**
     * @param name current name of this user
     * @param port that listen this
     * @param status current status of this user
     */
    public ChatUserData(String name, Integer port, String status) {
        this.name = name;
        this.port = port;
        this.status = status;
    }
}


