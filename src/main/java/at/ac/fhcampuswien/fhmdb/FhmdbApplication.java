package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;

public class FhmdbApplication extends Application {
    public static DatabaseManager databaseManager;

    @Override
    public void start(Stage stage) {
        databaseManager = new DatabaseManager();
        databaseManager.createConnectionSource();
        databaseManager.createTables();
        SceneManager.setStage(stage);
        SceneManager.switchScene("home-view.fxml");
    }

    @Override
    public void stop() {
        // Clean up database connection
        if (databaseManager != null) {
            databaseManager.closeConnectionSource();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}