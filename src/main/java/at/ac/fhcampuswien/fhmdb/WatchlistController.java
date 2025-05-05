package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.infrastructure.MovieEntity;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.services.MovieRepository;
import at.ac.fhcampuswien.fhmdb.services.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXListView;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WatchlistController {
    @FXML private MenuItem homeMenuItem;
    @FXML private MenuItem watchlistMenuItem;
    @FXML private MenuItem aboutMenuItem;

    @FXML
    private JFXListView<Movie> watchlistView;

    private DatabaseManager databaseManager;
    private MovieRepository movieRepository;
    private WatchlistRepository watchlistRepository;
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void logError(String message, Exception e) {
        System.err.println("[ERROR] " + message);
        e.printStackTrace();
    }


    @FXML
    public void initialize() {
        databaseManager = FhmdbApplication.databaseManager;
        movieRepository = new MovieRepository(databaseManager.getMovieDao());
        watchlistRepository = new WatchlistRepository(databaseManager.getWatchlistDao());

        try {
            initializeMoviesFromWatchlist();
        } catch (Exception e) {
            showError("Fehler beim Laden der Watchlist", "Die Filme konnten nicht geladen werden.");
            logError("Fehler beim Initialisieren der Watchlist", e);
        }

        watchlistView.setCellFactory(listView -> new MovieCell(
                movie -> {
                    try {
                        watchlistRepository.removeFromWatchlist(movie.getId());
                        initializeMoviesFromWatchlist();
                    } catch (MovieNotFoundException e) {
                        showError("Film nicht gefunden", "Der Film konnte nicht von der Watchlist entfernt werden.");
                        logError("Film nicht gefunden: " + movie.getId(), e);
                    } catch (SQLException e) {
                        showError("Datenbankfehler", "Beim Entfernen des Films ist ein Fehler aufgetreten.");
                        logError("SQL-Fehler beim Entfernen von Film mit ID: " + movie.getId(), e);
                    }
                    watchlistView.refresh();
                },
                movie -> true,
                false
        ));
    }




    private void initializeMoviesFromWatchlist() throws SQLException{
        //watchlistView.setItems(WatchlistRepository.getInstance().getWatchlist());
        var watchlist = watchlistRepository.getWatchlist();
        List<MovieEntity> moviesFromWatchList = new ArrayList<>();
        for(WatchListMovieEntity entity : watchlist) {
            MovieEntity movieEntity = movieRepository.getMovie(entity.getApiId());
            if(movieEntity != null) {
                moviesFromWatchList.add(movieEntity);
            }
        }

        List<Movie> newMovies = MovieEntity.toMovies(moviesFromWatchList);
        observableMovies.clear();
        observableMovies.addAll(newMovies);
        watchlistView.setItems(observableMovies);
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
