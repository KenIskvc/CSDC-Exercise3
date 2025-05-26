package at.ac.fhcampuswien.fhmdb.contracts;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import javafx.collections.ObservableList;

import java.util.List;

public interface ISortedSate {
    ISortedSate next() ;
    void sort(ObservableList<Movie> movies);
    SortedState getState();
    String getStateName();
}
