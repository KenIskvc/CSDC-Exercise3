package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.infrastructure.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.services.WatchlistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistRepositoryTest {

    private DatabaseManager dbManager;
    private WatchlistRepository watchlistRepo;

    @BeforeEach
    void setup() {
        dbManager = new DatabaseManager();
        dbManager.createConnectionSource();
        dbManager.createTables();
        watchlistRepo = new WatchlistRepository(dbManager.getWatchlistDao());
    }

    @AfterEach
    void tearDown() {
        dbManager.closeConnectionSource();
    }

    @Test
    void testRetrieveAndAddToWatchlist() {
        WatchListMovieEntity movie = new WatchListMovieEntity("apiTest123");
        int result = watchlistRepo.addToWatchlist(movie);

        List<WatchListMovieEntity> watchlist = watchlistRepo.getWatchlist();

        assertEquals(1, result);
        assertEquals(1, watchlist.size());
    }



    @Test
    void testRemoveFromWatchlist() {
        WatchListMovieEntity movie = new WatchListMovieEntity("apiTest789");
        watchlistRepo.addToWatchlist(movie);

        int deleted = watchlistRepo.removeFromWatchlist("apiTest789");

        List<WatchListMovieEntity> watchlist = watchlistRepo.getWatchlist();

        assertEquals(1, deleted);
    }
}