module org.csc480.roadtoeagle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires jdk.compiler;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.desktop;
    opens csc480 to javafx.fxml;
//    exports csc480;
    exports csc480.controller;
    opens csc480.controller to javafx.fxml;
    exports csc480.model;
    opens csc480.model to javafx.fxml;
    exports csc480.app;
    opens csc480.app to javafx.fxml;
    opens csc480.service to javafx.fxml;
    exports csc480.service;
    opens csc480.repository.mongo to javafx.fxml;
    exports csc480.repository.mongo;
}