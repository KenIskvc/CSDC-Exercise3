package at.ac.fhcampuswien.fhmdb.infrastructure;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchListMovieEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private String apiId;

    public WatchListMovieEntity() {
    }

    public WatchListMovieEntity(long id, String apiId) {
        this.id = id;
        this.apiId = apiId;
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
}
