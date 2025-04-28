package at.ac.fhcampuswien.fhmdb.infrastructure;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "movies")
public class MovieEntity {

    @DatabaseField(generatedId = true) // <- This field will be the PRIMARY KEY and auto-increment
    private long id;

    @DatabaseField
    private String apiId;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String genres;

    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private String imgUrl;

    @DatabaseField
    private int lengthInMinutes;

    @DatabaseField
    private double rating;

    public MovieEntity() {}
    public MovieEntity(String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public String genresToString(List<Genre> genres) {
        String genresString = "";
        for (Genre genre : genres) {
            if(!genresString.isEmpty()) {
                genresString += ",";
            }
            genresString = genresString + genre;
        }
        return genresString;
    }

    public List<Genre> stringToGenres(String genres) {
        List<Genre> genreList = new ArrayList<>();
        if (genres != null && !genres.isEmpty()) {
            String[] genreStrings = genres.split(",");
            for (String genreStr : genreStrings) {
                try {
                    genreList.add(Genre.valueOf(genreStr.trim().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    // Skip invalid genres or log an error if needed
                }
            }
        }
        return genreList;
    }

    public List<MovieEntity> fromMovies(List<Movie> movies) {
        List<MovieEntity> movieEntities = new ArrayList<>();
        for (Movie movie : movies) {
            MovieEntity entity = new MovieEntity(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDescription(),
                    genresToString(movie.getGenres()),
                    movie.getReleaseYear(),
                    movie.getImgUrl(),
                    movie.getLengthInMinutes(),
                    movie.getRating()
            );
            movieEntities.add(entity);
        }
        return movieEntities;
    }

    public List<Movie> toMovies(List<MovieEntity> movieEntities) {
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity entity : movieEntities) {
            Movie movie = new Movie(
                    entity.getApiId(),
                    entity.getTitle(),
                    entity.getDescription(),
                    stringToGenres(entity.getGenres()),
                    entity.getReleaseYear(),
                    entity.getImgUrl(),
                    entity.getLengthInMinutes(),
                    entity.getRating()
            );
        }

        return movies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
