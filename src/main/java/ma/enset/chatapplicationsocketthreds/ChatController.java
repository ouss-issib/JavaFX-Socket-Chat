package ma.enset.chatapplicationsocketthreds;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class ChatController {
    @FXML private TextArea chatArea;
    @FXML private TextField messageField;

    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private String username;

    public void initialize() {
        askForUsername();
        new Thread(this::connectToServer).start();
    }

    private void askForUsername() {
        TextInputDialog dialog = new TextInputDialog("User");
        dialog.setTitle("Enter Username");
        dialog.setHeaderText("Choose a display name");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> username = name);

        if (username == null || username.trim().isEmpty()) {
            username = "Anonymous";
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 9091);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);

            pw.println(username); // Send username to server

            new Thread(() -> {
                try {
                    String message;
                    while ((message = br.readLine()) != null) {
                        updateChatArea(message);
                    }
                } catch (IOException e) {
                    updateChatArea("⚠️ Connection lost.");
                }
            }).start();

        } catch (IOException e) {
            updateChatArea("⚠️ Unable to connect to server.");
        }
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && pw != null) {
            pw.println(message);
            messageField.clear();
        }
    }

    private void updateChatArea(String message) {
        Platform.runLater(() -> chatArea.appendText(message + "\n"));
    }

    public void stop() throws Exception {
        if (socket != null) socket.close();
    }
}
