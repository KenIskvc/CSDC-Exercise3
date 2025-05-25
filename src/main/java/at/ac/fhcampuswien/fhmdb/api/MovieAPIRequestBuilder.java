package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;

public class MovieAPIRequestBuilder {
    private static final String BASE_URL = "http://prog2.fh-campuswien.ac.at/movies";
    private static final String DELIMITER = "&";

    private String query;
    private Genre genre;
    private String releaseYear;
    private String ratingFrom;
    private String id;

    public MovieAPIRequestBuilder() {}

    public MovieAPIRequestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public MovieAPIRequestBuilder query(String query) {
        this.query = query;
        return this;
    }

    public MovieAPIRequestBuilder genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }

    public String build() {
        StringBuilder url = new StringBuilder(BASE_URL);

        if (id != null && !id.isEmpty()) {
            url.append("/").append(id);
            return url.toString(); // keine Query-Parameter
        }

        boolean hasParameter = false;
        url.append("?");

        if (query != null && !query.isEmpty()) {
            url.append("query=").append(query).append(DELIMITER);
            hasParameter = true;
        }

        if (genre != null) {
            url.append("genre=").append(genre).append(DELIMITER);
            hasParameter = true;
        }

        if (releaseYear != null && !releaseYear.isEmpty()) {
            url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            hasParameter = true;
        }

        if (ratingFrom != null && !ratingFrom.isEmpty()) {
            url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            hasParameter = true;
        }

        // Wenn kein Parameter gesetzt wurde, entfernen wir das "?"
        if (!hasParameter) {
            return BASE_URL;
        }

        // Entferne letztes "&", falls vorhanden
        if (url.charAt(url.length() - 1) == '&') {
            url.deleteCharAt(url.length() - 1);
        }

        return url.toString();
    }
}
