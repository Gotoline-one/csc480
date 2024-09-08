package csc480.controller;

import csc480.model.Award;
import csc480.model.Scout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

public class ScoutToAwardController extends SubController<Scout>{
    private MainController mainController;

    @FXML
    ListView<Award> availableAwards;
    ObservableList<Award> oAvailableAwards;

    @FXML
    ListView<Award> chosenAwards;
    ObservableList<Award> oChosenAwards;

    private Scout currentScout;
    private Award currentAward;
    private ObservableList<Award> selectedAwards;


    @FXML
    void initialize() {
        this.mainController = VistaNavigator.getMainController();
        if(mainController == null) return;
        currentScout = mainController.currentScoutSelected;
        Award currentAward = null;
        oAvailableAwards = FXCollections.observableArrayList();
        availableAwards.setItems(oAvailableAwards);
        availableAwards.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        oAvailableAwards.addAll(mainController.getAwardList().getItems());

        oChosenAwards = FXCollections.observableArrayList();
        chosenAwards.setItems(oChosenAwards);
        chosenAwards.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }


    @FXML
    public void selectAvailAwards(MouseEvent click) {
        if(click.getClickCount() ==2){ //Double Clicked

            mainController.setRightStatus("selectAvailAwards Clicked ");
            Award aAward = availableAwards.getSelectionModel().getSelectedItem();

            if(aAward !=null)
            { //something was double clicked
                currentAward = aAward;
                oChosenAwards.addAll(aAward);
                mainController.setLeftStatus("selectAvailAwards clicked add: "+ aAward);
            }
            else
            {//emptiness was double clicked so there is no award to select
                mainController.setRightStatus("selectAvailAwards Clicked empty Click");
            }
        }
    }
    @FXML
    public void addSelectedAward(){

        if( availableAwards.getSelectionModel().getSelectedItem() !=null){
            selectedAwards =  availableAwards.getSelectionModel().getSelectedItems();
            oChosenAwards.addAll(selectedAwards);
        }
    }

    public void removeSelectedAward() {
        selectedAwards =  chosenAwards.getSelectionModel().getSelectedItems();
        ObservableList<Integer> selectedIndices = chosenAwards.getSelectionModel().getSelectedIndices();
        // select 1 above deleted item if removing 1 item.
        // if removing 2, goto top of list
        int sel;
        if (selectedAwards.size() > 1)
            sel=0;
        else
            sel = Math.max(chosenAwards.getSelectionModel().getSelectedIndex()-1, 0);

        for (int i = selectedIndices.size() - 1; i >= 0; i--) {
            int selectedIndex = selectedIndices.get(i);
            chosenAwards.getItems().remove(selectedIndex);
        }

        chosenAwards.getSelectionModel().clearSelection();
        chosenAwards.getSelectionModel().select(Math.max(sel, 0));
    }

    @FXML
    public void saveBtn(){
        if(chosenAwards == null || currentScout == null) return;

        currentScout.clearAwards();
        for (Award b : chosenAwards.getItems()) {
            currentScout.addAward(b);
        }
        oChosenAwards.clear();
        currentAward = null;
        NewScoutController newScoutController = (NewScoutController) VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
        if(newScoutController !=null)
            newScoutController.loadInfo(this.currentScout);
    }

    public void cancelBtn() {
        oChosenAwards.clear();
        currentAward = null;
        NewScoutController newScoutController = (NewScoutController) VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
        if(newScoutController !=null)
            newScoutController.loadInfo(this.currentScout);
    }

    @Override
    public void clearInfo() {

    }

    @Override
    public void loadInfo(Scout scoutToLoad) {
        this.currentScout = scoutToLoad;
        oChosenAwards.addAll(this.currentScout.getAwards());
    }



}
