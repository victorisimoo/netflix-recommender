module netflix.recommender {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;

    opens com.netflix.app;
    opens com.netflix.controller;
    opens com.netflix.view;
}