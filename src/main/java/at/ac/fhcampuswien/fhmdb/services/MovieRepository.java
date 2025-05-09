package at.ac.fhcampuswien.fhmdb.services;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseOperationException;
import at.ac.fhcampuswien.fhmdb.exceptions.InvalidGenreException;
import at.ac.fhcampuswien.fhmdb.infrastructure.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.utilities.ExceptionUtility;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private Dao<MovieEntity, Long> dao;

    public MovieRepository(Dao<MovieEntity, Long> dao) {
        this.dao = dao;
    }

    public List<MovieEntity> getAllMovies() throws DatabaseOperationException {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            ExceptionUtility.logError("Database Error while fetching all movies", e);
            throw new DatabaseOperationException("An internal error occured.");
        }
    }

    public MovieEntity getMovie(String apiId) throws DatabaseOperationException {
        try {
            return dao.queryBuilder()
                    .where()
                    .eq("apiId", apiId)
                    .queryForFirst();
        } catch (Exception e) {
            ExceptionUtility.logError("Database Error while deleting all movies", e);
            throw new DatabaseOperationException("An internal error occured.");
        }
    }

    public int removeAll() throws DatabaseOperationException {
        try {
            List<MovieEntity> movies = dao.queryForAll();
            int count = 0;
            for (MovieEntity movie : movies) {
                count += dao.delete(movie);
            }
            return count;
        } catch (SQLException e) {
            ExceptionUtility.logError("Database Error while deleting all movies", e);
            throw new DatabaseOperationException("An internal error occured.");
        }
    }

    public int addAllMovies(List<Movie> movies) throws DatabaseOperationException {
        int count = 0;
        for (Movie movie : movies) {
            try {
                MovieEntity existing = dao.queryBuilder()
                        .where()
                        .eq("apiId", movie.getId())
                        .queryForFirst();

                if (existing != null) {
                    continue;
                }

                MovieEntity entity = new MovieEntity();
                entity.setApiId(movie.getId());
                entity.setTitle(movie.getTitle());
                entity.setDescription(movie.getDescription());
                entity.setGenres(entity.genresToString(movie.getGenres()));
                entity.setReleaseYear(movie.getReleaseYear());
                entity.setRating(movie.getRating());
                entity.setImgUrl(movie.getImgUrl());
                entity.setLengthInMinutes(movie.getLengthInMinutes());
                dao.create(entity);
                count++;
            } catch (InvalidGenreException e) {
                ExceptionUtility.logError(e.getMessage(), e);
                throw new DatabaseOperationException(e.getMessage());
            } catch (SQLException e) {
                ExceptionUtility.logError("Database Error occured while adding movies", e);
                throw  new DatabaseOperationException("An internal error occured while adding movies");
            }
        }
        return count;
    }



}
