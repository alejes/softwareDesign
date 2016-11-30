package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {
    private final Thread managerThread;
    private final ServerSocket socket;
    private final Logger log;
    private int myPort;

    public ChatServer(ChatUserData userData) throws IOException {
        log = Logger.getLogger(ChatServer.class.getName());
        try {
            socket = new ServerSocket(0);
            myPort = socket.getLocalPort();
            ChatServerManager csm = new ChatServerManager(socket, userData);
            managerThread = new Thread(csm);
            managerThread.start();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    public int getMyPort() {
        return myPort;
    }

    public void stopServer() {
        try {
            socket.close();
            managerThread.interrupt();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}