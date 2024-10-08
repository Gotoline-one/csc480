package csc480.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

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
    public static final String MAIN         = "/csc480/roadtoeagle.fxml";
    public static final String SPLASH       = "/csc480/Splash.fxml";
    public static final String NEW_SCOUT    = "/csc480/NewScout.fxml";
    public static final String NEW_ACTION   = "/csc480/NewActivity.fxml";
    public static final String NEW_BADGE    = "/csc480/NewBadge.fxml";
    public static final String NEW_EVENT    = "/csc480/NewScoutEvent.fxml";
    public static final String NEW_AWARD    = "/csc480/NewAward.fxml";
    public static final String SCOUT_BADGE  = "/csc480/ScoutToBadge.fxml";
    public static final String SCOUT_AWARD =  "/csc480/ScoutToAward.fxml";


    /**
     * The main application layout controller.
     */

//    private static SubController currentSubController;
//
//    public static SubController getSubController() {
//        if (currentSubController != null) {
//            return currentSubController;
//        }
//        return null;
//    }
//
//    public static void setSubController(SubController subController) {
//        VistaNavigator.currentSubController = subController;
//    }


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
    public static SubController loadVista(String fxml) {
        Locale en_US = new Locale("en");

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("csc480.Bundle", en_US);
            FXMLLoader loader = new FXMLLoader( VistaNavigator.class.getResource(fxml), bundle);
            Node node = loader.load();
            mainController.setVista(node);
            return  (SubController) loader.getController();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}