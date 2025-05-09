package at.ac.fhcampuswien.fhmdb.infrastructure;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseConnectionException;
import at.ac.fhcampuswien.fhmdb.exceptions.TableCreationException;
import at.ac.fhcampuswien.fhmdb.exceptions.DaoCreationException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    private final String DB_URL = "jdbc:h2:file:./db/movies";
    private final String user = "sa";
    private final String password = "";

    private ConnectionSource conn;
    private Dao<MovieEntity, Long> movieDao;
    private Dao<WatchListMovieEntity, Long> watchListDao;

    public void createConnectionSource() {
        try {
            conn = new JdbcConnectionSource(DB_URL, user, password);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to database at: " + DB_URL, e);
        }
    }

    public ConnectionSource getConnectionSource() {
        if (conn == null) {
            throw new IllegalStateException("Connection source is not initialized. Call createConnectionSource() first.");
        }
        return conn;
    }

    public void closeConnectionSource() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DatabaseConnectionException("Failed to close database connection.", e);
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
            throw new TableCreationException("Failed to create database tables.", e);
        }
    }

    public Dao<MovieEntity, Long> getMovieDao() {
        if (movieDao == null) {
            try {
                movieDao = DaoManager.createDao(conn, MovieEntity.class);
            } catch (SQLException e) {
                throw new DaoCreationException("Failed to create DAO for MovieEntity.", e);
            }
        }
        return movieDao;
    }

    public Dao<WatchListMovieEntity, Long> getWatchlistDao() {
        if (watchListDao == null) {
            try {
                watchListDao = DaoManager.createDao(conn, WatchListMovieEntity.class);
            } catch (SQLException e) {
                throw new DaoCreationException("Failed to create DAO for WatchListMovieEntity.", e);
            }
        }
        return watchListDao;
    }
}
