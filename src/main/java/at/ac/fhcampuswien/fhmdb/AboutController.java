package at.ac.fhcampuswien.fhmdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Momentan keine Initialisierung notwendig.
        // Hier kannst du später dynamisch Text oder Daten einfügen, falls gewünscht.
    }

    @FXML
    public void switchToHome(ActionEvent event) {
        SceneManager.switchScene((Node) event.getSource(), "home-view.fxml", ViewMode.HOME);
    }

    @FXML
    public void switchToWatchlist(ActionEvent event) {
        SceneManager.switchScene((Node) event.getSource(), "home-view.fxml", ViewMode.WATCHLIST);
    }


    @FXML
    public void switchToAbout(ActionEvent event) {
        // Du bist bereits auf der About-Page – optional könnte hier ein Hinweis kommen oder einfach nichts passieren.
        // Hier absichtlich leer lassen oder vielleicht eine kleine Info-Logik einbauen, wenn du willst.
    }
}
