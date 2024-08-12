package csc480;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class for controlling navigation between vistas.
 * <p>
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class VistaNavigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    public static final String MAIN = "RoadtoEagle.fxml";
    public static final String SPLASH = "Splash.fxml";
    public static final String VISTA_2 = "NewScout_i18n.fxml";


    /**
     * The main application layout controller.
     */
    private static MainController mainController;

    public static MainController getMainController() {
        if (mainController != null) {
            return mainController;
        }
        return null;
    }

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     * <p>
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     * <p>
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     * cache FXMLLoaders
     * cache loaded vista nodes, so they can be recalled or reused
     * allow a user to specify vista node reuse or new creation
     * allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadVista(String fxml) {
        Locale fr_FR = new Locale("fr");
        Locale en_US = new Locale("en");

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("csc480.Bundle", en_US);
            mainController.setVista(
                    FXMLLoader.load(VistaNavigator.class.getResource(fxml), bundle)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}