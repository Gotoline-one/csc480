package csc480.controller;

import csc480.model.Activity;
import csc480.model.Badge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class NewBadgeController extends SubController<Badge>{

    private MainController mainController;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField activityTitle;
    @FXML
    private CheckBox knowledgeCkBx;
    @FXML
    private CheckBox actionCkBx;
    @FXML
    private TextArea descriptionBox;
    @FXML
    private Badge currentBadge;

    @FXML
    private ListView<Activity> activitiesList;
    ObservableList<Activity> availActivitiesListObserver;

    @FXML
    ListView<Activity> availActivitiesList;
    ObservableList<Activity> activitiesListObserver;

    ObservableList<Activity> selectedActivities;

    Activity currentActivity;

    public void initialize () {
        this.mainController = VistaNavigator.getMainController();
        if(mainController == null) return;
        this.currentBadge   = null;
        selectedActivities  = null;
        currentActivity = null;

        activitiesListObserver = FXCollections.observableArrayList();
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
     * @return if Badge object is complete
     */

    private boolean formToBadge(){
        if(this.currentBadge ==null)
            currentBadge = new Badge();
        currentBadge.setBadgeName(activityTitle.getText());
        currentBadge.setKnowBased(knowledgeCkBx.isSelected());
        currentBadge.setPhysical(actionCkBx.isSelected());
        currentBadge.setBadgeDescription(descriptionBox.getText());
        currentBadge.activityAddAll(activitiesListObserver);

        return true;

    }
    @FXML
    public void saveNewBtn(ActionEvent actionEvent) {
        currentBadge = new Badge();
        formToBadge();
        mainController.addBadge(this.currentBadge);
        this.currentBadge = null;
        clearInfo();
    }

    @FXML
    public void addActivityBtn(ActionEvent actionEvent) {
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
        currentBadge = null;
    }


    @Override
    public void loadInfo(Badge newBadge) {
        if(newBadge ==null)
            return;
        currentBadge = newBadge;
        activityTitle.setText(currentBadge.getBadgeName());
        knowledgeCkBx.setSelected(currentBadge.isKnowBased());
        actionCkBx.setSelected(currentBadge.isPhysical());
        descriptionBox.setText(currentBadge.getBadgeDescription());
        activitiesListObserver.addAll(newBadge.getActivityList());
    }


    @FXML
    void cancelBtn(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        this.currentBadge = null;
        clearInfo();

        if(mainController !=null) {
            mainController.currentScoutSelected = null;
            this.mainController.setLeftStatus("NewBadgeController.Cancel()");
        }
    }



    public void saveBtn(ActionEvent actionEvent) {
        if(currentBadge ==null)
            currentBadge = new Badge();

        if(formToBadge())
        {// the form is complete and no errors need to be shown to user
            if(mainController !=null)
            {
                mainController.saveBadge(this.currentBadge);
                mainController.badgeList.refresh();
            }
        }
        else
        {
            if(mainController !=null)
                this.mainController.setLeftStatus("issue with new Badge form saveBtn()");
        }
        if(mainController !=null)
            this.mainController.setLeftStatus("NewBadgeController.saveBadge()");
    }




}
