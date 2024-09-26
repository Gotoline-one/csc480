package csc480.controller;

import csc480.model.Badge;
import csc480.model.Scout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

public class ScoutToBadgeController extends SubController<Scout>{
    private MainController mainController;

    @FXML
    ListView<Badge> availableBadges;
    ObservableList<Badge> oAvailableBadges;

    @FXML
    ListView<Badge> chosenBadges;
    ObservableList<Badge> oChosenBadges;

    private Scout currentScout;
//    private Badge currentBadge;
    private ObservableList<Badge> selectedBadges;


    @FXML
    void initialize() {
        this.mainController = VistaNavigator.getMainController();
        if(mainController == null) return;
        currentScout = mainController.currentScoutSelected;
        Badge currentBadge = null;
        oAvailableBadges = FXCollections.observableArrayList();
        availableBadges.setItems(oAvailableBadges);
        availableBadges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        oAvailableBadges.addAll(mainController.getBadgeList().getItems());

        oChosenBadges = FXCollections.observableArrayList();
        chosenBadges.setItems(oChosenBadges);
        chosenBadges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }


    @FXML
    public void selectAvailBadges(MouseEvent click) {
        if(click.getClickCount() ==2){ //Double Clicked

            mainController.setRightStatus("selectAvailBadges Clicked ");
            Badge aBadge = availableBadges.getSelectionModel().getSelectedItem();

            if(aBadge !=null)
            { //something was double clicked
                if(oChosenBadges.contains(aBadge)){
                    mainController.setRightStatus(aBadge +" Already Added to Scout ");
                    return;
                }
                oChosenBadges.add(aBadge);
                mainController.setLeftStatus("selectAvailBadges clicked add: "+ aBadge);
            }
            else
            {//emptiness was double clicked so there is no badge to select
                mainController.setRightStatus("Clicked empty on empty Item");
            }
        }
    }

    @FXML
    public void addSelectedBadge(){

        if( availableBadges.getSelectionModel().getSelectedItem() !=null){
            selectedBadges =  availableBadges.getSelectionModel().getSelectedItems();
            // Only add badges that have not already been added
            for( Badge selBadge : selectedBadges){
                if(!oChosenBadges.contains(selBadge))
                    oChosenBadges.add(selBadge);
            }
        }
    }

    public void removeSelectedBadge() {
        selectedBadges =  chosenBadges.getSelectionModel().getSelectedItems();
        ObservableList<Integer> selectedIndices = chosenBadges.getSelectionModel().getSelectedIndices();
        // select 1 above deleted item if removing 1 item.
        // if removing 2, goto top of list
        int sel;
        if (selectedBadges.size() > 1)
            sel=0;
        else
            sel = Math.max(chosenBadges.getSelectionModel().getSelectedIndex()-1, 0);

        for (int i = selectedIndices.size() - 1; i >= 0; i--) {
            int selectedIndex = selectedIndices.get(i);
            chosenBadges.getItems().remove(selectedIndex);
        }

        chosenBadges.getSelectionModel().clearSelection();
        chosenBadges.getSelectionModel().select(Math.max(sel, 0));
    }

    @FXML
    public void saveBtn(){
        if(chosenBadges == null || currentScout == null) return;

        currentScout.clearBadges();
        for (Badge b : chosenBadges.getItems()) {
            currentScout.addMeritBadge(b);
        }
        oChosenBadges.clear();
//        currentBadge = null;
        NewScoutController newScoutController = (NewScoutController) VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
        if(newScoutController !=null)
            newScoutController.loadInfo(this.currentScout);
    }

    public void cancelBtn() {
        oChosenBadges.clear();
//        currentBadge = null;
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
        oChosenBadges.addAll(this.currentScout.getMeritBadges());
    }



}
