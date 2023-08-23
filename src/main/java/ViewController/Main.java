package ViewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.Objects;
import javafx.scene.Scene;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("FXConfig.fxml")));

        stage.setTitle("Documents classification");
        stage.setScene(new Scene(root, 800, 600));
        stage.setOnCloseRequest(event -> {
            FxController.es.shutdown();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}