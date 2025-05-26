package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.DuplicateMovieException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.observer.Observer;
import at.ac.fhcampuswien.fhmdb.services.WatchlistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistRepositoryTest {

    private DatabaseManager dbManager;
    private WatchlistRepository watchlistRepo;
    private TestObserver observer;

    @BeforeEach
    void setup() {
        dbManager = new DatabaseManager();
        dbManager.createConnectionSource();
        dbManager.createTables();
        watchlistRepo = new WatchlistRepository(dbManager.getWatchlistDao());

        observer = new TestObserver();
        watchlistRepo.addObserver(observer);
    }

    @AfterEach
    void tearDown() {
        watchlistRepo.removeObserver(observer);  // saubere Abmeldung
        dbManager.closeConnectionSource();
    }

    @Test
    void testRetrieveAndAddToWatchlist_andObserverGetsNotified() throws SQLException, DuplicateMovieException, MovieNotFoundException {
        String apiId = "apiTest123";
        if (watchlistRepo.isOnWatchList(apiId)) {
            watchlistRepo.removeFromWatchlist(apiId);
        }

        WatchListMovieEntity movie = new WatchListMovieEntity(apiId);
        int result = watchlistRepo.addToWatchlist(movie);

        assertEquals(1, result);
        assertTrue(observer.message.contains("erfolgreich"));
    }

    @Test
    void testRemoveFromWatchlist_andObserverGetsNotified() throws SQLException, DuplicateMovieException, MovieNotFoundException {
        String apiId = "apiTest789";
        if (!watchlistRepo.isOnWatchList(apiId)) {
            watchlistRepo.addToWatchlist(new WatchListMovieEntity(apiId));
        }

        int deleted = watchlistRepo.removeFromWatchlist(apiId);

        assertEquals(1, deleted);
        assertTrue(observer.message.contains("entfernt"));
    }

    static class TestObserver implements Observer {
        String message = "";

        @Override
        public void update(String message) {
            this.message = message;
        }
    }
}
