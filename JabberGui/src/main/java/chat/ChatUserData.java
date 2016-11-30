package chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatUserData {
    public String name = "Unknown";
    public Integer port = 0;
    public String status = "";
    public final LinkedList<ChatMessage> messages = new LinkedList<>();

}


