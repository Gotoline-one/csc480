package csc480.controller;

import csc480.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController {

    @FXML
    ListView<Scout> scoutList;
    ObservableList<Scout> scoutListObserver;
    Scout currentScoutSelected;
//    ArrayList<Activity> activityArrayList;
//    @FXML
//    ListView<ScoutEvent> eventList;
//    ObservableList<ScoutEvent> eventListObserver;
    @FXML
    ListView<Badge> badgeList;
    ObservableList<Badge> badgeListObserver;
//    @FXML
//    ListView<Award> awardList;
//    ObservableList<Award> awardListObserver;
    @FXML
    Label rightStatus;
    @FXML
    Label leftStatus;
    private SubController currentNewScoutController;
    @FXML
    private StackPane mainScreen;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML

    private Button button;
    private Badge currentBadgeSelected;
    private SubController currentNewBadgeController;
//    private ScoutEvent currentScoutEventSelected;
    private Object currentNewScoutEventController;
//    private Award currentAwardSelected;
    private DataController dataController;
//    private ListView<Activity> scoutActivities;
    @FXML
    void initialize() throws Exception {
        scoutList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Link Observers to Lists
        scoutListObserver = FXCollections.observableArrayList();
        scoutList.setItems(scoutListObserver);

        badgeListObserver = FXCollections.observableArrayList();
        badgeList.setItems(badgeListObserver);

        currentNewBadgeController = null;
        currentBadgeSelected = null;

        currentNewScoutEventController = null;
//        currentScoutEventSelected = null;

        currentNewScoutController = null;
        currentScoutSelected = null;

//        currentAwardSelected = null;
//        scoutActivities = new ListView<Activity>();
//        addFakeActions();
        addFakeScouts();
        System.out.println("MainController Initialized");
        dataController = new DataController(this);
        ArrayList<Badge> fakeBadges = dataController.getAllBadges();
        badgeListObserver.addAll(fakeBadges);



    }


//    public ListView<Activity> getScoutActivities() {
//        return scoutActivities;
//    }

//    public void addFakeActions() {
//
//        scoutActivities.getItems().add(new Activity("5 Mi Hike"));
//        scoutActivities.getItems().add(new Activity("10 Mi Hike"));
//        scoutActivities.getItems().add(new Activity("20 Mi Hike"));
//        scoutActivities.getItems().add(new Activity("Explain how hiking is an aerobic activity"));
//
//    }

//    public void addFakeBadges(){
//        for(int i = 0; i < scoutActivities.getItems().size(); i++){
//            badgeList.getItems().add(
//                    //new Badge("badgeName"+Integer.toString(i), scoutActivities.getItems().subList(0, i)  )
//                    new Badge("Some Badge 1", "\\\t1. Some requirement",true)
//            );
//        }
//    }
//    public void addFakeAwards(){
//        for(int i = 0; i < scoutActivities.getItems().size(); i++){
//            awardList.getItems().add(
//                    new Award("awardName"+Integer.toString(i), scoutActivities.getItems().subList(0, i)  )
//            );
//        }
//    }
    public void addFakeScouts() {
        //Scout(String newFirstName, String newLastname, String newRank, String newPosition, String newEmail)
        String vari = "aaa";
        scoutList.getItems().add(
                new Scout(
                        "fname_" + vari,
                        "lname_" + vari,
                        "rank_" + vari,
                        "pos_" + vari,
                        vari + "@" + vari + ".com"
                ));
        vari = "bbb";
        scoutList.getItems().add(
                new Scout(
                        "fname_" + vari,
                        "lname_" + vari,
                        "rank_" + vari,
                        "pos_" + vari,
                        vari + "@" + vari + ".com"
                ));
        vari = "ccc";
        scoutList.getItems().add(
                new Scout(
                        "fname_" + vari,
                        "lname_" + vari,
                        "rank_" + vari,
                        "pos_" + vari,
                        vari + "@" + vari + ".com"
                ));
        vari = "ddd";
        scoutList.getItems().add(
                new Scout(
                        "fname_" + vari,
                        "lname_" + vari,
                        "rank_" + vari,
                        "pos_" + vari,
                        vari + "@" + vari + ".com"
                ));
    }

    public void setLeftStatus(String leftStatus) {
        this.leftStatus.setText(leftStatus);
    }

    public void setRightStatus(String rightStatus) {
        this.rightStatus.setText(rightStatus);
    }

    @FXML
    void newMerit() {
        VistaNavigator.loadVista(VistaNavigator.NEW_BADGE);

    }

    public void setVista(Node node) {
        mainScreen.getChildren().setAll(node);
    }


    @FXML
    public void newEvent(ActionEvent actionEvent) {
        VistaNavigator.loadVista(VistaNavigator.NEW_EVENT);

    }

    @FXML
    void newActivity() {
        VistaNavigator.loadVista(VistaNavigator.NEW_ACTION);

    }


    /**
     * checks if newScout is a valid object,
     * adds newly passed in Scout to scoutList if not already on list
     *
     * @param newScout Scout object to copy and add to scoutList
     */
    public void addScout(Scout newScout) {
        if (newScout == null) return;

        if (!scoutListObserver.contains(newScout)) {
            scoutListObserver.add(newScout);
            scoutList.getSelectionModel().clearSelection();
            setRightStatus("add Scout(" + newScout + ")");
            currentScoutSelected = null;
        }
    }

    public void saveScout(Scout updatedScout) {
        if (updatedScout == null) return;

        if (currentScoutSelected != null) {
            currentScoutSelected.updateScout(updatedScout);
        } else {
            addScout(updatedScout);
        }
        scoutList.getSelectionModel().clearSelection();
        scoutList.getSelectionModel().select(updatedScout);
        setRightStatus("Updated Scout(" + updatedScout + ")");
    }


    @FXML
    void newScoutMenuBtn() throws IOException {
        currentScoutSelected = new Scout();
        currentNewScoutController = VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);

    }

    @FXML
    public void selectScoutBadge(MouseEvent click) {
        if (click.getClickCount() == 2) { //Double Clicked

            setRightStatus("NewScoutBadge ");
            Badge aBadge = badgeList.getSelectionModel().getSelectedItem();

            if (aBadge != null) { //something was double clicked
                currentBadgeSelected = aBadge;
                currentNewBadgeController = VistaNavigator.loadVista(VistaNavigator.NEW_BADGE);
                NewBadgeController newBadgeController = (NewBadgeController) currentNewBadgeController;
                if (newBadgeController != null)
                    newBadgeController.loadInfo(aBadge);
            } else {//emptiness was double clicked so there is no badge to select
                currentNewBadgeController = VistaNavigator.loadVista(VistaNavigator.NEW_BADGE);
                currentScoutSelected = null;
            }
        }
    }

    @FXML
    public void selectScout(MouseEvent click) {

        if (click.getClickCount() == 2) {
            Scout aScout = scoutList.getSelectionModel().getSelectedItem();
            if (aScout != null)  //something was double clicked
            {
                currentScoutSelected = aScout;
                currentNewScoutController = VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
                if (currentNewScoutController != null) {
                    NewScoutController newScoutController = (NewScoutController) currentNewScoutController;
                    newScoutController.loadInfo(currentScoutSelected);
                } else {
                    setLeftStatus("ERROR Could not load New Scout Page");
                }
            } else    //emptiness was double clicked so there is no scout to select
            {
                currentNewScoutController = VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
                currentScoutSelected = null;
            }
            setRightStatus("Selected Scout(" + aScout + ")");
        }

    }

    public void menuQuit(ActionEvent event) throws IOException {
        Platform.exit();
    }


    public void addBadge(Badge currentBadge) {
        if (currentBadge == null) return;

        if (!badgeListObserver.contains(currentBadge)) {//if list does not already contain this award
            badgeListObserver.add(currentBadge);
            badgeList.getSelectionModel().clearSelection();
            setRightStatus("add Award(" + currentBadge + ")");
            currentBadgeSelected = null;
        } else {
            setRightStatus("ERROR IN ADDING Badge - award already exists");
        }

    }

    public void saveBadge(Badge newBadge) {
        if (newBadge == null) return;

        if (currentBadgeSelected != null)
            currentBadgeSelected.updateBadge(newBadge);
        else
            this.addBadge(newBadge);
    }

    public ListView<Badge> getBadgeList() {
        return badgeList;
    }

//    public ListView<Award> getAwardList() {
//        return awardList;
//    }

    public void saveMenuBtn() {
//        if(scoutList !=null && awardList !=null  && badgeList !=null  ){
        if(scoutList !=null && badgeList !=null  ){
            dataController.saveAll(new ArrayList<>(scoutList.getItems()),new ArrayList<>(badgeList.getItems()));
        }
        else{
            if(scoutList != null) {
                dataController.saveAll(new ArrayList<>(scoutList.getItems()));
            }
        }
    }
}