package csc480.controller;

import csc480.model.*;
import csc480.repository.BadgeRepository;
import csc480.repository.mongo.Connection;
import csc480.repository.mongo.MongoBadgeRepo;
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
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    ListView<Scout> scoutList;
    ObservableList<Scout> scoutListObserver;
    Scout currentScoutSelected;
    ArrayList<Activity> activityArrayList;
    @FXML
    ListView<ScoutEvent> eventList;
    ObservableList<ScoutEvent> eventListObserver;
    @FXML
    ListView<Badge> badgeList;
    ObservableList<Badge> badgeListObserver;
    @FXML
    ListView<Award> awardList;
    ObservableList<Award> awardListObserver;
    @FXML
    Label rightStatus;
    @FXML
    Label leftStatus;
    private SubController currentNewScoutController;
    @FXML
    private HBox mainScreen;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML

    private Button button;
    private Badge currentBadgeSelected;
    private SubController currentNewBadgeController;
    private ScoutEvent currentScoutEventSelected;
    private Object currentNewScoutEventController;
    private Award currentAwardSelected;
    private NewAwardController currentNewAwardController;

    private ListView<Activity> scoutActivities;
    private Connection myconnection;
    @FXML
    void initialize() throws Exception {
        scoutList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Link Observers to Lists
        scoutListObserver = FXCollections.observableArrayList();
        scoutList.setItems(scoutListObserver);

        eventListObserver = FXCollections.observableArrayList();
        eventList.setItems(eventListObserver);

        badgeListObserver = FXCollections.observableArrayList();
        badgeList.setItems(badgeListObserver);

        awardListObserver = FXCollections.observableArrayList();
        awardList.setItems(awardListObserver);

        currentNewBadgeController = null;
        currentBadgeSelected = null;

        currentNewScoutEventController = null;
        currentScoutEventSelected = null;

        currentNewScoutController = null;
        currentScoutSelected = null;

        currentNewAwardController = null;
        currentAwardSelected = null;
        scoutActivities = new ListView<Activity>();
        addFakeActions();
        addFakeScouts();
        addFakeBadges();
        addFakeAwards();
        System.out.println("MainController Initialized");

        try (MongoBadgeRepo badgeRepository = new MongoBadgeRepo()) {
            ArrayList<Badge> badgeList = badgeRepository.findAll();
            for(Badge b : badgeList){
                System.out.println(b + " ");
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }


    }

    public ListView<Activity> getScoutActivities() {
        return scoutActivities;
    }

    public void addFakeActions() {

        scoutActivities.getItems().add(new Activity("5 Mi Hike"));
        scoutActivities.getItems().add(new Activity("10 Mi Hike"));
        scoutActivities.getItems().add(new Activity("20 Mi Hike"));
        scoutActivities.getItems().add(new Activity("Explain how hiking is an aerobic activity"));

    }
    public void addFakeBadges(){
        for(int i = 0; i < scoutActivities.getItems().size(); i++){
            badgeList.getItems().add(
                    new Badge("badgeName"+Integer.toString(i), scoutActivities.getItems().subList(0, i)  )
            );
        }
    }
    public void addFakeAwards(){
        for(int i = 0; i < scoutActivities.getItems().size(); i++){
            awardList.getItems().add(
                    new Award("awardName"+Integer.toString(i), scoutActivities.getItems().subList(0, i)  )
            );
        }
    }
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
    void newScoutMenuBtn(ActionEvent event) throws IOException {
        currentNewScoutController = VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
        currentScoutSelected = null;
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
    public void selectScoutAward(MouseEvent click) {
        if (click.getClickCount() == 2) { //Double Clicked

            setRightStatus("NewScoutAward ");
            Award aAward = awardList.getSelectionModel().getSelectedItem();

            if (aAward != null) { //something was double clicked
                currentAwardSelected = aAward;
                currentNewAwardController = (NewAwardController) VistaNavigator.loadVista(VistaNavigator.NEW_AWARD);
                NewAwardController newAwardController = currentNewAwardController;
                if (newAwardController != null)
                    newAwardController.loadInfo(aAward);
            } else {//emptiness was double clicked so there is no badge to select
                currentNewAwardController = (NewAwardController) VistaNavigator.loadVista(VistaNavigator.NEW_AWARD);
                currentScoutSelected = null;
            }
        }
    }

    @FXML
    public void selectScoutEvent(MouseEvent click) {

        if (click.getClickCount() == 2) {
            ScoutEvent aScoutEvent = eventList.getSelectionModel().getSelectedItem();
            if (aScoutEvent != null)  //something was double clicked
            {
                currentScoutEventSelected = aScoutEvent;
                currentNewScoutController = VistaNavigator.loadVista(VistaNavigator.NEW_EVENT);
                NewScoutEventController scoutEventController = (NewScoutEventController) currentNewScoutEventController;
                if (scoutEventController != null)
                    scoutEventController.loadInfo(currentScoutEventSelected);

            } else    //emptiness was double clicked so there is no scout to select
            {
                currentNewScoutController = VistaNavigator.loadVista(VistaNavigator.NEW_EVENT);
                currentScoutEventSelected = null;
            }
            setRightStatus("Selected Scout(" + aScoutEvent + ")");
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

    public void newAward(ActionEvent actionEvent) {
        currentNewAwardController = (NewAwardController) VistaNavigator.loadVista(VistaNavigator.NEW_AWARD);
        currentScoutSelected = null;
    }

    public void saveAward(Award newAward) {
        if (newAward == null) return;

        if (currentAwardSelected != null)
            currentAwardSelected.updateAward(newAward);
        else
            this.addAward(newAward);
    }

    public void addAward(Award currentAward) {
        if (currentAward == null) return;

        if (!awardListObserver.contains(currentAward)) {//if list does not already contain this award
            awardListObserver.add(currentAward);
            awardList.getSelectionModel().clearSelection();
            setRightStatus("add Award(" + currentAward + ")");
            currentAwardSelected = null;
        } else {
            setRightStatus("ERROR IN ADDING AWARD - award already exists");
        }
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

    public ListView<Award> getAwardList() {
        return awardList;
    }
}