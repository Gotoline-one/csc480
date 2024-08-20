package csc480;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for the first vista.
 */
public class SplashController extends SubController<Integer>{

    /**
     * Event handler fired when the user requests a new vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void nextPane(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);

    }

    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo(Integer obj) {

    }
}