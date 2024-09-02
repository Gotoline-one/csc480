package csc480;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class NewBadgeController extends SubController<Badge>{
    MainController mainController;


    private Badge currentBadge;
    @FXML
    private ListView<Activity> activityList;
    ObservableList<Activity> activityListObserver;


    @FXML
    private ListView<Activity> availActivityList;
    ObservableList<Activity> availActivityListObserver;

    public void initialize () {
        this.mainController = VistaNavigator.getMainController();
        currentBadge = null;


    }
    @Override
    public void clearInfo() {

    }


    @Override
    public void loadInfo(Badge obj) {

    }


    @FXML
    void cancelBtn(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        this.currentBadge = null;
        clearInfo();
        if(mainController !=null)
            this.mainController.setLeftStatus("NewBadgeController.Cancel()");

    }
}
