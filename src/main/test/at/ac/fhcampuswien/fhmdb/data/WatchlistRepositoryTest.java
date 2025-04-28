package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.exceptions.DuplicateMovieException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistRepositoryTest {

    private WatchlistRepository watchlistRepository;
    private Movie testMovie;

    @BeforeEach
    void setUp() {
        watchlistRepository = WatchlistRepository.getInstance();
        watchlistRepository.getWatchlist().clear();  // sicherstellen, dass leer

        testMovie = new Movie(
                null,
                "Test Movie",
                "A test description",
                List.of(Genre.DRAMA),
                2025,
                "",
                120,
                8.5
        );
    }


    @Test
    void addMovie_successfully() throws DuplicateMovieException {
        watchlistRepository.addToWatchlist(testMovie);
        assertTrue(watchlistRepository.getWatchlist().contains(testMovie));
    }

    @Test
    void addMovie_duplicate_throwsException() throws DuplicateMovieException {
        watchlistRepository.addToWatchlist(testMovie);
        DuplicateMovieException exception = assertThrows(DuplicateMovieException.class, () -> {
            watchlistRepository.addToWatchlist(testMovie);
        });
        assertEquals("Movie already exists in the watchlist: " + testMovie.getTitle(), exception.getMessage());
    }

    @Test
    void removeMovie_successfully() throws DuplicateMovieException, MovieNotFoundException {
        watchlistRepository.addToWatchlist(testMovie);
        watchlistRepository.removeFromWatchlist(testMovie);
        assertFalse(watchlistRepository.getWatchlist().contains(testMovie));
    }

    @Test
    void removeMovie_notFound_throwsException() {
        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> {
            watchlistRepository.removeFromWatchlist(testMovie);
        });
        assertEquals("Movie not found in the watchlist: " + testMovie.getTitle(), exception.getMessage());
    }

    @Test
    void isOnWatchlist_returnsCorrectly() throws DuplicateMovieException {
        assertFalse(watchlistRepository.isOnWatchlist(testMovie));
        watchlistRepository.addToWatchlist(testMovie);
        assertTrue(watchlistRepository.isOnWatchlist(testMovie));
    }

    @Test
    void addNullMovie_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            watchlistRepository.addToWatchlist(null);
        });
        assertEquals("Cannot add null movie to watchlist.", exception.getMessage());
    }

    @Test
    void removeNullMovie_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            watchlistRepository.removeFromWatchlist(null);
        });
        assertEquals("Cannot remove null movie from watchlist.", exception.getMessage());
    }
}
