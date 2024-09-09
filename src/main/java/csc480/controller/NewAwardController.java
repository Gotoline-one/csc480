package csc480.controller;

import csc480.model.Activity;
import csc480.model.Award;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class NewAwardController extends SubController<Award>{
    private MainController mainController;
    private Award currentAward;

    ObservableList<Activity> selectedActivities;

    @FXML
    ListView<Activity> activitiesList;
    ObservableList<Activity> activitiesListObserver;

    @FXML
    ListView<Activity> availActivitiesList;
    ObservableList<Activity> availActivitiesListObserver;

    @FXML
    TextField activityTitle = null;

    @FXML
    CheckBox actionCkBx;

    @FXML
    CheckBox knowledgeCkBx;

    @FXML
    TextArea descriptionBox;
    Activity currentActivity;

    public void initialize (){
        this.mainController = VistaNavigator.getMainController();
        if(mainController == null) return;
        this.currentAward   = null;
        selectedActivities  = null;
        currentActivity = null;

        activitiesListObserver      = FXCollections.observableArrayList();

        availActivitiesListObserver = FXCollections.observableArrayList();
        availActivitiesListObserver.addAll(mainController.getScoutActivities().getItems());

        if(activitiesList !=null) {
            activitiesList.setItems(activitiesListObserver);
            availActivitiesList.setItems(availActivitiesListObserver);
        }

        availActivitiesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        activitiesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }


    @FXML
    public void selectAvailActivity(MouseEvent click) {
        if(click.getClickCount() ==2){ //Double Clicked

            mainController.setRightStatus("NewAvailActivity Clicked ");
            Activity aActivity = availActivitiesList.getSelectionModel().getSelectedItem();

            if(aActivity !=null)
            { //something was double clicked
                currentActivity = aActivity;
                activitiesListObserver.addAll(aActivity);
                mainController.setLeftStatus("NewAvailActivity clicked add: "+ aActivity);
            }
            else
            {//emptiness was double clicked so there is no badge to select
                mainController.setRightStatus("NewAvailActivity Clicked empty Click");
            }
        }
    }

    /**
     * TODO: this is a good basic start, but need to finish all the same kind of logic that is in NewScoutController.formToScout
     *
     * @return if Award object is complete
     */

    private boolean formToAward(){
            if(this.currentAward ==null)
                currentAward = new Award();
            currentAward.setTitle(activityTitle.getText());
            currentAward.setKnowledge(knowledgeCkBx.isSelected());
            currentAward.setActBase(actionCkBx.isSelected());
            currentAward.setAwardDescription(descriptionBox.getText());
            currentAward.activityAddAll(activitiesListObserver);

            return true;

    }
    @FXML
    public void saveNewBtn()
    {
        currentAward = new Award();
        formToAward();
        mainController.addAward(this.currentAward);
        this.currentAward = null;
        clearInfo();
    }

    @FXML
    public void addActivityBtn(){

        selectedActivities = availActivitiesList.getSelectionModel().getSelectedItems();
        activitiesListObserver.addAll(selectedActivities);
    }

    @FXML
    public void removeActivity()
    {

        selectedActivities =  activitiesList.getSelectionModel().getSelectedItems();
        ObservableList<Integer> selectedIndices = activitiesList.getSelectionModel().getSelectedIndices();
        // select 1 above deleted item if removing 1 item.
        // if removing 2, goto top of list
        int sel;
        if (selectedActivities.size() > 1)
            sel=0;
        else
            sel = Math.max(activitiesList.getSelectionModel().getSelectedIndex()-1, 0);

        for (int i = selectedIndices.size() - 1; i >= 0; i--) {
            int selectedIndex = selectedIndices.get(i);
            activitiesList.getItems().remove(selectedIndex);
        }

        activitiesList.getSelectionModel().clearSelection();
        activitiesList.getSelectionModel().select(Math.max(sel, 0));
    }

    @Override
    public void clearInfo()
    {
        activityTitle.setText("");
        knowledgeCkBx.setSelected(false);
        actionCkBx.setSelected(false);
        descriptionBox.setText("");
        activitiesListObserver.clear();
        currentAward = null;
    }


    @Override
    public void loadInfo(Award obj) {
        if(obj ==null)
            return;
        currentAward = obj;
        activityTitle.setText(currentAward.getAwardName());
        knowledgeCkBx.setSelected(currentAward.isKnowBased());
        actionCkBx.setSelected(currentAward.isActBase());
        descriptionBox.setText(currentAward.getAwardDescription());
        for( Activity act : obj){
            activitiesListObserver.add(act);
        }
    }


    @FXML
    void cancelBtn(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        this.currentAward = null;
        clearInfo();

        if(mainController !=null) {
            mainController.currentScoutSelected = null;
            this.mainController.setLeftStatus("NewScoutController.Canceled()");
        }
    }

    public void saveBtn(ActionEvent actionEvent) {
        if(currentAward ==null)
            currentAward = new Award();

        if(formToAward())
        {// the form is complete and no errors need to be shown to user
            if(mainController !=null)
            {
                mainController.saveAward(this.currentAward);
                mainController.awardList.refresh();
            }
        }
        else
        {
            if(mainController !=null)
                this.mainController.setLeftStatus("issue with new Award form saveBtn()");
        }
        if(mainController !=null)
            this.mainController.setLeftStatus("NewAwardController.saveAward()");
    }
}
