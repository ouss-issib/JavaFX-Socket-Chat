package ma.enset.chatapplicationsocketthreds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader user1Loader = new FXMLLoader(ChatApplication.class.getResource("user1-view.fxml"));
        FXMLLoader user2Loader = new FXMLLoader(ChatApplication.class.getResource("user2-view.fxml"));

        // Load both scenes
        Scene user1Scene = new Scene(user1Loader.load());
        Scene user2Scene = new Scene(user2Loader.load());

        // Setup User 1 Window
        Stage user1Stage = new Stage();
        user1Stage.setTitle("Profile -> Issib Oussama");
        user1Stage.setScene(user1Scene);
        user1Stage.show();

        // Setup User 2 Window
        Stage user2Stage = new Stage();
        user2Stage.setTitle("Profile -> Zouita Salah");
        user2Stage.setScene(user2Scene);
        user2Stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
