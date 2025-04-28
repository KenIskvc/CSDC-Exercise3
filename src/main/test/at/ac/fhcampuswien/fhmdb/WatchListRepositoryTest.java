package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.DuplicateMovieException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.services.MovieRepository;
import at.ac.fhcampuswien.fhmdb.services.WatchlistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistRepositoryTest {

    private DatabaseManager dbManager;
    private WatchlistRepository watchlistRepo;
    private MovieRepository movieRepo;

    @BeforeEach
    void setup() {
        dbManager = new DatabaseManager();
        dbManager.createConnectionSource();
        dbManager.createTables();
        watchlistRepo = new WatchlistRepository(dbManager.getWatchlistDao());
        movieRepo = new MovieRepository(dbManager.getMovieDao());
    }

    @AfterEach
    void tearDown() {
        dbManager.closeConnectionSource();

    }

    @Test
    void testRetrieveAndAddToWatchlist() throws SQLException, DuplicateMovieException, MovieNotFoundException {
        watchlistRepo.removeFromWatchlist("apiTest123");
        WatchListMovieEntity movie = new WatchListMovieEntity("apiTest123");
        int result = watchlistRepo.addToWatchlist(movie);

        List<WatchListMovieEntity> watchlist = watchlistRepo.getWatchlist();

        assertEquals(1, result);
    }



    @Test
    void testRemoveFromWatchlist() throws SQLException, DuplicateMovieException, MovieNotFoundException {
        WatchListMovieEntity movie = new WatchListMovieEntity("apiTest789");
        watchlistRepo.addToWatchlist(movie);

        int deleted = watchlistRepo.removeFromWatchlist("apiTest789");

        List<WatchListMovieEntity> watchlist = watchlistRepo.getWatchlist();

        assertEquals(1, deleted);
    }
}