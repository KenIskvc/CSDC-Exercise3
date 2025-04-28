package at.ac.fhcampuswien.fhmdb.services;

import at.ac.fhcampuswien.fhmdb.infrastructure.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private Dao<MovieEntity, Long> dao;

    public MovieRepository(Dao<MovieEntity, Long> dao) {
        this.dao = dao;
    }

    public List<MovieEntity> getAllMovies() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public MovieEntity getMovie(String apiId) {
        try {
            return dao.queryBuilder()
                    .where()
                    .eq("apiId", apiId)
                    .queryForFirst();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int removeAll() {
        try {
            List<MovieEntity> movies = dao.queryForAll();
            int count = 0;
            for (MovieEntity movie : movies) {
                count += dao.delete(movie);
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addAllMovies(List<Movie> movies) {
        int count = 0;
        for (Movie movie : movies) {
            try {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }



}
