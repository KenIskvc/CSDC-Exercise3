package at.ac.fhcampuswien.fhmdb.services;

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

    public int addToWatchlist(WatchListMovieEntity movie) {
        try {
            dao.create(movie);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeFromWatchlist(String apiId) {
        try {
            List<WatchListMovieEntity> movies = dao.queryForEq("apiId", apiId);
            int deleted = 0;
            for (WatchListMovieEntity movie : movies) {
                deleted += dao.delete(movie);
            }
            return deleted;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
