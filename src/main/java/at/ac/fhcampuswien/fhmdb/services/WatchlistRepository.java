package at.ac.fhcampuswien.fhmdb.services;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseAccessException;
import at.ac.fhcampuswien.fhmdb.exceptions.DuplicateMovieException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {

    private Dao<WatchListMovieEntity, Long> dao;

    public WatchlistRepository(Dao<WatchListMovieEntity, Long> dao) {
        this.dao = dao;
    }

    public List<WatchListMovieEntity> getWatchlist() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseAccessException("Failed to fetch watchlist", e);
        }
    }


    public int addToWatchlist(WatchListMovieEntity movie) throws DuplicateMovieException, SQLException {
        if (isOnWatchList(movie.getApiId())) {
            throw new DuplicateMovieException("Movie already exists in the watchlist: " + movie.getApiId());
        }

        return dao.create(movie); // gibt 1 zurück, falls erfolgreich
    }


    public int removeFromWatchlist(String apiId) throws MovieNotFoundException, SQLException {
        List<WatchListMovieEntity> movies = dao.queryForEq("apiId", apiId);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movie found with apiId: " + apiId);
        }

        int deleted = 0;
        for (WatchListMovieEntity movie : movies) {
            deleted += dao.delete(movie);
        }
        return deleted;
    }


    public boolean isOnWatchList(String apiId) {
        try {
            WatchListMovieEntity existing = dao.queryBuilder()
                    .where()
                    .eq("apiId", apiId)
                    .queryForFirst();
            return existing != null;
        } catch (SQLException e) {
            throw new RuntimeException("Database error while checking watchlist", e);
        }
    }


}
