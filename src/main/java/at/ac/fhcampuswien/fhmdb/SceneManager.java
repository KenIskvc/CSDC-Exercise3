package at.ac.fhcampuswien.fhmdb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    public static void switchScene(Node sourceNode, String fxmlFileName, ViewMode viewMode) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFileName));
            Parent root = loader.load();

            // Controller holen und ViewMode setzen
            Object controller = loader.getController();
            if (controller instanceof HomeController && viewMode != null) {
                ((HomeController) controller).setViewMode(viewMode);
            }

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root, 890, 620);
            scene.getStylesheets().add(SceneManager.class.getResource("styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Fehler beim Wechseln der Seite: " + e.getMessage());
        }
    }


    private static void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
