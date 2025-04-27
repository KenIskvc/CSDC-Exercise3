package at.ac.fhcampuswien.fhmdb;

import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.switchScene("home-view.fxml");
    }


    public static void main(String[] args) {
        launch();
    }
}