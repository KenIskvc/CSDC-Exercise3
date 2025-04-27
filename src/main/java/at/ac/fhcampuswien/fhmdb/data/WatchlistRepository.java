package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.exceptions.DuplicateMovieException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WatchlistRepository {
    private static WatchlistRepository instance;
    private final ObservableList<Movie> watchlist;

    private WatchlistRepository() {
        this.watchlist = FXCollections.observableArrayList();
    }

    public static WatchlistRepository getInstance() {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
    }

    public ObservableList<Movie> getWatchlist() {
        return watchlist;
    }

    public void addToWatchlist(Movie movie) throws DuplicateMovieException {
        if (movie == null) {
            throw new IllegalArgumentException("Cannot add null movie to watchlist.");
        }
        if (watchlist.contains(movie)) {
            throw new DuplicateMovieException("Movie already exists in the watchlist: " + movie.getTitle());
        }
        watchlist.add(movie);
    }

    public void removeFromWatchlist(Movie movie) throws MovieNotFoundException {
        if (movie == null) {
            throw new IllegalArgumentException("Cannot remove null movie from watchlist.");
        }
        if (!watchlist.contains(movie)) {
            throw new MovieNotFoundException("Movie not found in the watchlist: " + movie.getTitle());
        }
        watchlist.remove(movie);
    }

    public boolean isOnWatchlist(Movie movie) {
        return watchlist.contains(movie);
    }
}
