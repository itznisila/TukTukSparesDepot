package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {
        Label label = new Label("Spares Depot");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Malabe Tuk-Tuk & Three-Wheeler Spares Depot");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
