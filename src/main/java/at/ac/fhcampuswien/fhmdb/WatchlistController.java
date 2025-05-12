package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseOperationException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieMappingException;
import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.infrastructure.MovieEntity;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.services.MovieRepository;
import at.ac.fhcampuswien.fhmdb.services.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.utilities.ExceptionUtility;
import javafx.application.Platform;
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
    @FXML private JFXListView<Movie> watchlistView;

    private DatabaseManager databaseManager;
    private MovieRepository movieRepository;
    private WatchlistRepository watchlistRepository;
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        databaseManager = FhmdbApplication.databaseManager;
        movieRepository = new MovieRepository(databaseManager.getMovieDao());
        watchlistRepository = new WatchlistRepository(databaseManager.getWatchlistDao());

        try {
            initializeMoviesFromWatchlist();

            /** Auskommentieren um die ExceptionUtility zu testen

            ExceptionUtility.showError("Vorzeige Alert", "Das ist ein Beispiel Alert");
            ExceptionUtility.logError("Das ist ein Beispiel Log", new Exception("Beispiel Exception"));

             */
        } catch (Exception e) {
            ExceptionUtility.showError("Fehler beim Laden der Watchlist", "Die Filme konnten nicht geladen werden.");
            ExceptionUtility.logError(e.getMessage(), e);
        }

        watchlistView.setCellFactory(listView -> new MovieCell(
                movie -> {
                    try {
                        watchlistRepository.removeFromWatchlist(movie.getId());
                        initializeMoviesFromWatchlist();
                    } catch (MovieNotFoundException e) {
                        ExceptionUtility.showError("Film nicht gefunden", "Der Film konnte nicht von der Watchlist entfernt werden.");
                        ExceptionUtility.logError("Film nicht gefunden: " + movie.getId(), e);
                    } catch (SQLException e) {
                        ExceptionUtility.showError("Datenbankfehler", "Beim Entfernen des Films ist ein Fehler aufgetreten.");
                        ExceptionUtility.logError("SQL-Fehler beim Entfernen von Film mit ID: " + movie.getId(), e);
                    } catch (MovieMappingException e) {
                        ExceptionUtility.showError("Initialisierungsfehler", e.getMessage());
                        ExceptionUtility.logError(e.getMessage(), e);
                    } catch (DatabaseOperationException e) {
                        ExceptionUtility.showError("Datenbankfehler", e.getMessage());
                    }
                    watchlistView.refresh();
                },
                movie -> true,
                false
        ));
    }

    private void initializeMoviesFromWatchlist() throws SQLException, MovieMappingException, DatabaseOperationException {
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
