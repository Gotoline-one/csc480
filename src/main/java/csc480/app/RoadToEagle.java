package csc480.app;

//import csc480.Branched.Activity;
import csc480.controller.MainController;
import csc480.controller.VistaNavigator;
import csc480.model.Scout;
import csc480.service.ScoutService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
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
                getClass().getResourceAsStream(VistaNavigator.MAIN)
        );

        MainController mainController = loader.getController();
        VistaNavigator.setMainController(mainController);

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
                getClass().getResource("/csc480/vista.css").toExternalForm()
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
        primaryStage = stage;
        stage.show();
    }


    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return !m.matches();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Window owner =  primaryStage.getScene().getWindow();
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }


    /**
     *  NOTE: IF NEEDED this is where the threading would go
     *  Get Array List of Scouts from Mongo or JSON file
     * @return ArrayList of Scout Objects from Scout Service
     */
    public ArrayList<Scout> getScouts(){
        ScoutService scoutService = new ScoutService();
        return scoutService.findAll();
    }

    public boolean saveScout(Scout scout){
        ScoutService scoutService = new ScoutService();
        return scoutService.updateScout(scout);
    }

    public boolean saveScouts(ArrayList<Scout> scouts){
        ScoutService scoutService = new ScoutService();
        return scoutService.updateScouts(scouts);
    }

}