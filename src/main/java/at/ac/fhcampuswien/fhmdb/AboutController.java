package at.ac.fhcampuswien.fhmdb;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class AboutController {

    @FXML private MenuItem homeMenuItem;
    @FXML private MenuItem watchlistMenuItem;
    @FXML private MenuItem aboutMenuItem;

    // 💡 WICHTIG für ControllerFactory: expliziter leerer Konstruktor
    public AboutController() {
        // Keine Initialisierung notwendig
    }

    @FXML
    private void goToHome() {
        SceneManager.switchScene("home-view.fxml");
    }

    @FXML
    private void goToWatchlist() {
        SceneManager.switchScene("watchlist-view.fxml");
    }

    @FXML
    private void goToAbout() {
        SceneManager.switchScene("about-view.fxml");
    }
}
