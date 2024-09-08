package csc480.controller;

import csc480.model.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewActivityController extends SubController<Activity>{

    private MainController mainController;

    @FXML
    TextField titleTxtBox;
    @FXML
    TextArea activityDescTxtBx;
    @FXML
    CheckBox knowledgeCkBx;
    @FXML
    CheckBox actionCkBx;

    @FXML
    void initialize() {

        this.mainController = VistaNavigator.getMainController();
    }


    @FXML
    void closeWoSaving(){
        //Clear everything
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
    }

    @FXML
    void closeAndSaving(){
        //Save everything
        //go through everything and save to new Activity type
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
    }


    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo(Activity activity){
        titleTxtBox.setText(activity.getName());
        activityDescTxtBx.setText(activity.getDescription());
        actionCkBx.setSelected(activity.isActionBased());
        knowledgeCkBx.setSelected(activity.isKnowledgeBased());
    }
}
