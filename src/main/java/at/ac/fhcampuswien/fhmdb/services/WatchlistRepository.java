package at.ac.fhcampuswien.fhmdb.services;

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
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int addToWatchlist(WatchListMovieEntity movie) throws DuplicateMovieException, SQLException {
        boolean exists = isOnWatchList(movie.getApiId());
        if(exists) {
            throw new DuplicateMovieException("Movie already exists in the watchlist: " + movie.getId());
        }
        dao.create(movie);
        return 1;
    }

    public int removeFromWatchlist(String apiId) throws MovieNotFoundException, SQLException {
        List<WatchListMovieEntity> movies = dao.queryForEq("apiId", apiId);
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
            e.printStackTrace();
            return true;
        }
    }

}
