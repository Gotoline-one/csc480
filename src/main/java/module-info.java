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
    opens csc480 to javafx.fxml;
    exports csc480;
}