package sample;

import chat.ChatClient;
import chat.ChatMessage;
import chat.ChatServer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controller for GUI client
 */
public class Controller implements Initializable {
    private final ChatClient client;
    private final Object polygonWriter = new Object();
    @FXML
    private TextField usernameText;
    @FXML
    private TextField statusText;
    @FXML
    private TextField portText;
    @FXML
    private TextField messageText;
    @FXML
    private TextField targetPort;
    @FXML
    private TextField targetIp;
    @FXML
    private TextArea polygon;

    private Thread mr;

    private Logger log;

    public Controller() throws IOException {
        log = Logger.getLogger(ChatServer.class.getName());
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
    public void messageSendButtonClicked() {
        String message = messageText.getCharacters().toString();
        String ip = targetIp.getCharacters().toString();
        Integer port = Integer.parseInt(targetPort.getCharacters().toString());
        client.sendMessage(ip, port, message);
        synchronized (polygonWriter) {
            polygon.setText("Message From [YOU] to " + port.toString() + ": " + message + "\n" + polygon.getText());
        }
    }

    @FXML
    public void statusGetButtonClicked() {
        Integer port = Integer.parseInt(targetPort.getCharacters().toString());
        String ip = targetIp.getCharacters().toString();
        String status = client.requestStatus(ip, port);
        synchronized (polygonWriter) {
            polygon.setText("Status of " + port.toString() + " is " + status + "\n" + polygon.getText());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Integer port = client.getPort();
        portText.setText(port.toString());
        MessageReader messageReader = new MessageReader();
        mr = new Thread(messageReader);
        mr.start();
    }

    public void setupScene(Stage stage) {
        stage.setOnCloseRequest(we -> {
            mr.interrupt();
            Platform.exit();
            System.exit(0);
        });
    }

    private class MessageReader implements Runnable {

        @Override
        public void run() {
            ChatMessage mes;
            synchronized (client.messages()) {
                while (!Thread.interrupted()) {
                    while ((mes = client.messages().poll()) != null) {
                        polygon.setText("Message From [" + mes.getAuthor() + ":" + mes.getPort() + "] to [YOU]: " + mes.getMessage() + "\n" + polygon.getText());
                    }
                    try {
                        client.messages().wait();
                    } catch (InterruptedException e) {
                        log.finer("Interrupt in message reader: " + e.getMessage());
                    }
                }
            }
        }
    }
}
