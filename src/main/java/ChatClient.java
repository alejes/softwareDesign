import org.jetbrains.annotations.Nullable;
import queries.GetStatusQuery;
import queries.QueriesBuilder;
import queries.QueryType;
import queries.SendMessageQuery;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {
    private final ChatServer server;
    private ChatUserData userData;

    ChatClient() throws IOException {
        userData = new ChatUserData();
        server = new ChatServer(userData);
    }

    public String getName() {
        return userData.name;
    }

    public void setName(String name) {
        userData.name = name;
    }

    public String getStatus() {
        return userData.status;
    }

    public void setStatus(String status) {
        userData.status = status;
    }

    public int getPort() {
        return server.getMyPort();
    }

    public void stopClient() {
        server.stopServer();
    }


    String requestStatus(int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("127.0.0.1", port), 5000);
            GetStatusQuery q = (GetStatusQuery) QueriesBuilder.build(QueryType.GET_STATUS);
            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                 DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                dos.writeUTF(q.genQuery());
                return q.resolveQuery(dis.readUTF());
            }
        } catch (IOException e) {
            Logger log = Logger.getLogger(ChatClient.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
            return "[cannot read status]";
        }
    }

    public void sendMessage(int port, @Nullable String message) {
        if (message != null) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("127.0.0.1", port), 5000);
                SendMessageQuery q = (SendMessageQuery) QueriesBuilder.build(QueryType.SEND_MESSAGE);
                try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                    dos.writeUTF(q.genQuery(message, userData));
                }
            } catch (IOException e) {
                Logger log = Logger.getLogger(ChatClient.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}