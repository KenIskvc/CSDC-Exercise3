package at.ac.fhcampuswien.fhmdb.utilities;

import javafx.scene.control.Alert;

public class ExceptionUtility {

    public static void showError(String title, String message) {

    }
    public static void logError(String message, Exception e) {
        System.err.println("[ERROR] " + message);
        e.printStackTrace();
    }
}
