module org.csc480.roadtoeagle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires jdk.compiler;
    opens csc480 to javafx.fxml;
    exports csc480;
}