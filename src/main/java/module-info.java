module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires ormlite.jdbc;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.infrastructure to ormlite.jdbc;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.infrastructure;
    exports at.ac.fhcampuswien.fhmdb.services;
    exports at.ac.fhcampuswien.fhmdb.observer;
    exports at.ac.fhcampuswien.fhmdb.exceptions;
}