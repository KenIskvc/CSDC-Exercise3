package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Function;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final JFXButton detailBtn = new JFXButton("Show Details");
    private final JFXButton actionBtn = new JFXButton();
    private final VBox layout = new VBox();
    private final VBox detailsBox = new VBox();
    private final HBox buttonBox = new HBox();
    private boolean collapsedDetails = true;
    private final ClickEventHandler<Movie> actionHandler;
    private final Function<Movie, Boolean> isMovieOnWatchlist;
    private final boolean isHomeView;

    public MovieCell(ClickEventHandler<Movie> actionHandler, Function<Movie, Boolean> isMovieOnWatchlist, boolean isHomeView) {
        this.actionHandler = actionHandler;
        this.isMovieOnWatchlist = isMovieOnWatchlist;
        this.isHomeView = isHomeView;

        // Farben und Layout
        title.getStyleClass().add("text-yellow");
        detail.getStyleClass().add("text-white");
        genre.getStyleClass().add("text-white");
        genre.setStyle("-fx-font-style: italic");

        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER_LEFT);

        title.setFont(Font.font(20));
        detail.setWrapText(true);

        buttonBox.setSpacing(10);

        detailBtn.setStyle("-fx-background-color: #f5c518; -fx-text-fill: black;");
        detailBtn.setOnMouseClicked(mouseEvent -> toggleDetails());

        actionBtn.setOnMouseClicked(mouseEvent -> {
            if (getItem() != null) {
                try {
                    actionHandler.onClick(getItem());
                    updateActionButton(getItem());
                } catch (Exception e) {
                    System.err.println("Error performing action: " + e.getMessage());
                }
            }
        });


        buttonBox.getChildren().addAll(detailBtn, actionBtn);
        layout.getChildren().addAll(title, detail, genre, buttonBox);
        setGraphic(layout);
    }

    private void toggleDetails() {
        if (collapsedDetails) {
            layout.getChildren().add(detailsBox);
            collapsedDetails = false;
            detailBtn.setText("Hide Details");
        } else {
            layout.getChildren().remove(detailsBox);
            collapsedDetails = true;
            detailBtn.setText("Show Details");
        }
    }

    private VBox createDetails(Movie movie) {
        VBox box = new VBox();
        box.setSpacing(5);

        Label releaseYear = new Label("Release Year: " + movie.getReleaseYear());
        Label length = new Label("Length: " + movie.getLengthInMinutes() + " minutes");
        Label rating = new Label("Rating: " + movie.getRating() + "/10");
        Label directors = new Label("Directors: " + String.join(", ", movie.getDirectors()));
        Label writers = new Label("Writers: " + String.join(", ", movie.getWriters()));
        Label mainCast = new Label("Main Cast: " + String.join(", ", movie.getMainCast()));

        releaseYear.getStyleClass().add("text-white");
        length.getStyleClass().add("text-white");
        rating.getStyleClass().add("text-white");
        directors.getStyleClass().add("text-white");
        writers.getStyleClass().add("text-white");
        mainCast.getStyleClass().add("text-white");

        box.getChildren().addAll(releaseYear, rating, length, directors, writers, mainCast);
        return box;
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            title.setText(movie.getTitle());
            detail.setText(movie.getDescription() != null ? movie.getDescription() : "No description available");
            genre.setText(movie.getGenres().stream().map(Enum::toString).collect(Collectors.joining(", ")));
            detail.setMaxWidth(this.getScene().getWidth() - 30);

            detailsBox.getChildren().clear();
            detailsBox.getChildren().addAll(createDetails(movie).getChildren());
            if (!collapsedDetails && !layout.getChildren().contains(detailsBox)) {
                layout.getChildren().add(detailsBox);
            }
            updateActionButton(movie);
            setGraphic(layout);
        }
    }

    private void updateActionButton(Movie movie) {
        if (isHomeView) {
            if (isMovieOnWatchlist.apply(movie)) {
                actionBtn.setText("On Watchlist");
                actionBtn.setStyle("-fx-background-color: #aaaaaa; -fx-text-fill: black;");
                actionBtn.setDisable(true);
            } else {
                actionBtn.setText("Add to Watchlist");
                actionBtn.setStyle("-fx-background-color: #f5c518; -fx-text-fill: black;");
                actionBtn.setDisable(false);
            }
        } else {
            actionBtn.setText("Remove from Watchlist");
            actionBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            actionBtn.setDisable(false);
        }
    }
}
