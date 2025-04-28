package at.ac.fhcampuswien.fhmdb.infrastructure;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    //jdbc:h2:file:C:/Users/kenan/Documents/university/2.semester/programming2/db/movies
    private final String DB_URL = "jdbc:h2:file:./db/movies";
    private String user = "sa";
    private String password = "";
    private ConnectionSource conn;
    private Dao<MovieEntity, Long> movieDao;
    private Dao<WatchListMovieEntity, Long> watchListDao;

    public void createConnectionSource() {
        try {
            conn = new JdbcConnectionSource(DB_URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSource getConnectionSource() {
        return conn;
    }

    public void closeConnectionSource() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createTables() {
        try {
            TableUtils.createTableIfNotExists(conn, MovieEntity.class);
            TableUtils.createTableIfNotExists(conn, WatchListMovieEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        if (movieDao == null) {
            try {
                movieDao = DaoManager.createDao(conn, MovieEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movieDao;
    }

    public Dao<WatchListMovieEntity, Long> getWatchlistDao() {
        if (watchListDao == null) {
            try {
                watchListDao = DaoManager.createDao(conn, WatchListMovieEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return watchListDao;
    }
}
