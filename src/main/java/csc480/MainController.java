package csc480;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class MainController {

    @FXML
    ListView<Scout> scoutList;
    ObservableList<Scout> scoutListObserver;

    SubController currentNewScoutController;

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
    private HBox mainScreen;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button button;

    @FXML
    void initialize() {
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

        SubController currentNewScoutController=null;

    }

    @FXML
    void newScoutBtn(ActionEvent event) throws IOException {
        VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);

    }

    @FXML
    void newMerit(){
        VistaNavigator.loadVista(VistaNavigator.NEW_BADGE);

    }

    public void addScout(Scout newScout) {
        scoutListObserver.add(newScout);
        System.out.println("Adding newScout " + newScout.toString());
    }

    public void saveScout(Scout updatedScout){
        System.out.println(currentScoutSelected + " VS  " + updatedScout + "  Before");
        currentScoutSelected.updateScout(updatedScout);
        System.out.println(currentScoutSelected + " VS  " + updatedScout + "  AFTER");

    }

    public void setVista(Node node) {
        mainScreen.getChildren().setAll(node);
    }

    public void menuQuit(ActionEvent event) throws IOException{
        Platform.exit();

    }

    public void newAction(ActionEvent actionEvent) {
        VistaNavigator.loadVista(VistaNavigator.NEW_ACTION);
    }

    Scout currentScoutSelected;
    @FXML
    public void scoutSelect(MouseEvent click) {

        if (click.getClickCount() == 2) {
            if(scoutList.getSelectionModel().getSelectedItem() !=null){
                currentScoutSelected = scoutList.getSelectionModel().getSelectedItem();
                System.out.println("you've Selected  " + currentScoutSelected);
               currentNewScoutController= VistaNavigator.loadVista(VistaNavigator.NEW_SCOUT);
                NewScoutController nsc= (NewScoutController) currentNewScoutController;
                nsc.loadInfo(currentScoutSelected);

            }
            else{
                System.out.println("you've Selected  a null item. How did yo do that?" );

            }

        }
    }
}