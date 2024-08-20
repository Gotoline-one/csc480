package csc480;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class NewActionController extends SubController<ScoutAction>{

    @FXML
    ListView assocBadges;

    @FXML
    void closeWoSaving(){
        //Clear everything
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
    }

    @FXML
    void closeAndSaving(){
        //Save everything
        //go through everything and save to new ScoutAction type
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
    }


    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo(ScoutAction scoutAction){
    }
}
