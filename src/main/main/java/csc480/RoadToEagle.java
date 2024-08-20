package csc480;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;



public class RoadToEagle extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch();

    }

    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Locale en_US = new Locale("en");
        ResourceBundle bundle = ResourceBundle.getBundle("csc480.Bundle", en_US);
        loader.setResources(bundle);

        Pane mainPane = loader.load(
                getClass().getResourceAsStream(VistaNavigator.MAIN) //MAIN = "roadtoeagle.fxml";
        );


        MainController mainController = loader.getController();

        VistaNavigator.setMainController(mainController);
        VistaNavigator.loadVista(VistaNavigator.SPLASH);

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);

        scene.getStylesheets().setAll(
                getClass().getResource("vista.css").toExternalForm()
        );

        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Vista Viewer");

        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.show();
    }

    @FXML
    void nextPane(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);

    }
}