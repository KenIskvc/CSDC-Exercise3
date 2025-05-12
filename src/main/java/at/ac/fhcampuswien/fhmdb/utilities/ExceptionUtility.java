package at.ac.fhcampuswien.fhmdb.utilities;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;

public class ExceptionUtility {

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // kein Header
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void logError(String message, Exception e) {
        System.err.println("[" + LocalDateTime.now() + "] [ERROR] " + message);
        e.printStackTrace();
    }
}
