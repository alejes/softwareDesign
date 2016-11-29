package sample;

import chat.ChatClient;
import chat.ChatServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.jnlp.IntegrationService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    private final ChatClient client;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField statusText;
    @FXML
    private TextField portText;
    @FXML
    private TextField sendText;
    @FXML
    private TextArea polygon;

    public Controller() throws IOException {
        Logger log = Logger.getLogger(ChatServer.class.getName());
        try {
            client = new ChatClient();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }

    @FXML
    public void usernameSavedButtonClicked() {
        client.setName(usernameText.getCharacters().toString());
    }

    @FXML
    public void statusSavedButtonClicked() {
        client.setStatus(statusText.getCharacters().toString());
    }

    @FXML
    public void commandSendButtonClicked() {
        String request = sendText.getCharacters().toString();
        QueryProcessor.processQuery(client, request, polygon);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Integer port = client.getPort();
        portText.setText(port.toString());
    }
}
