package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXListView;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.scene.control.MenuItem;


public class WatchlistController {
    @FXML private MenuItem homeMenuItem;
    @FXML private MenuItem watchlistMenuItem;
    @FXML private MenuItem aboutMenuItem;

    @FXML
    private JFXListView<Movie> watchlistView;

    @FXML
    public void initialize() {
        watchlistView.setItems(WatchlistRepository.getInstance().getWatchlist());
        watchlistView.setCellFactory(listView -> new MovieCell(
                movie -> {
                    try {
                        WatchlistRepository.getInstance().removeFromWatchlist(movie);
                    } catch (MovieNotFoundException e) {
                        System.err.println("Error removing movie from watchlist: " + e.getMessage());
                    }
                    watchlistView.refresh(); // UI sofort aktualisieren
                },
                movie -> true, // Weil alle Filme hier schon in der Watchlist sind
                false // nicht HomeView → WatchlistView
        ));
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
