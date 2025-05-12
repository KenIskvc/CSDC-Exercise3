package at.ac.fhcampuswien.fhmdb.services;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseAccessException;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseOperationException;
import at.ac.fhcampuswien.fhmdb.exceptions.DuplicateMovieException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieNotFoundException;
import at.ac.fhcampuswien.fhmdb.infrastructure.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.utilities.ExceptionUtility;
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
            ExceptionUtility.logError("Database Error while fetching watchlist", e);
            throw new DatabaseAccessException("Failed to fetch watchlist");
        }
    }


    public int addToWatchlist(WatchListMovieEntity movie) throws DuplicateMovieException, DatabaseOperationException {
        try{
            if (isOnWatchList(movie.getApiId())) {
                throw new DuplicateMovieException("Movie already exists in the watchlist: " + movie.getApiId());
            }

        return dao.create(movie);
        } catch (SQLException e) {
            ExceptionUtility.logError("Database Error while adding movie to watchlist", e);
            throw new DatabaseOperationException("Database error while adding movie to watchlist");
        }
    }


    public int removeFromWatchlist(String apiId) {
        try{
            List<WatchListMovieEntity> movies = dao.queryForEq("apiId", apiId);

        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movie found with apiId: " + apiId);
        }

        int deleted = 0;
        for (WatchListMovieEntity movie : movies) {
            deleted += dao.delete(movie);
        }
        return deleted;
    } catch (SQLException e) {
            ExceptionUtility.logError("Database Error while removing movie from watchlist", e);
            throw new DatabaseOperationException("Database error while removing movie from watchlist");
        }
    }



    public boolean isOnWatchList(String apiId) {
        try {
            WatchListMovieEntity existing = dao.queryBuilder()
                    .where()
                    .eq("apiId", apiId)
                    .queryForFirst();
            return existing != null;
        } catch (SQLException e) {
            ExceptionUtility.logError("Database Error while checking watchlist", e);
            throw new DatabaseOperationException("Database error while checking watchlist");
        }
    }


}
