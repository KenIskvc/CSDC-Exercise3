package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.factory.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static Stage stage;
    private static final ControllerFactory controllerFactory = new ControllerFactory();

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            loader.setControllerFactory(controllerFactory); // Factory Pattern
            Parent root = loader.load();
            Scene scene = new Scene(root, 890, 620);
            scene.getStylesheets().add(Objects.requireNonNull(SceneManager.class.getResource("styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene-Wechsel fehlgeschlagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
