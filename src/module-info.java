module netflix.recommender {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires org.json;

    opens com.netflix.app;
    opens com.netflix.controller;
    opens com.netflix.view;
}