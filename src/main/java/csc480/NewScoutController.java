package csc480;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * TODO: Finish Awards, Badges, and Photo stuff
 * TODO: Make Position a dropdown (There is a finite amount of scouting positions)
 */
public class NewScoutController extends SubController<Scout>{
    @FXML
    ComboBox<String> rankCombo;
    @FXML
    private TextField firstNameTxtBox;

    @FXML
    private Button cancelBtn;

    @FXML
    private ImageView scoutImage;

    @FXML
    private Button addBadgesBtn;

    @FXML
    private TextField lastNameTxtBox;

    @FXML
    private Button saveScoutBtn;

    @FXML
    private Button AddScoutPhotoBtn;

    @FXML
    private Button addAwardsBtn;

    @FXML
    private Button NextBtn;

    @FXML
    private TextField scoutPosition;

    @FXML
    private TextField scoutEmail;

    @FXML
    private TextField scoutAwards;

    @FXML
    private TextField scoutBadges;

    @FXML
    void addScoutPhoto(ActionEvent event) {

    }

    @FXML
    void addAwards(ActionEvent event) {

    }

    @FXML
    void addBadges(ActionEvent event) {

    }
    @FXML
    void initialize() {
        rankCombo.getItems().addAll("Tenderfoot","Second Class","First Class","Star","Life","Eagle");

    }

    /**
     * TODO: Could do some error checking here: email, ensure all the info is filled out
     *
     * @return New Scout object from form
     */
    private Scout formToScout(){
        Scout newScout = new Scout(firstNameTxtBox.getText(), lastNameTxtBox.getText(), rankCombo.getValue());
        newScout.setPosition(scoutPosition.getText());
        newScout.setEmail(scoutEmail.getText());

        return newScout;
    }

    /**
     * TODO: add data verification
     * TODO: make sure all fields are filled
     * TODO: make sure all fields make sense (Email RFC Check)
     * TODO: Add some kind of RED outline on problem fields, AND statement on bottom of screen.
     *
     *
     */
    @FXML
    void nextScout(ActionEvent event) {

        VistaNavigator.getMainController().addScout(formToScout());
        clearInfo();
    }

    @FXML
    void cancel(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        clearInfo();
    }

    /**
     * This function is to Save data to current Scout
     *
     */
    @FXML
    void saveScout(ActionEvent event) {

        VistaNavigator.getMainController().saveScout(formToScout());
    }


    @Override
    public void clearInfo() {
        firstNameTxtBox.clear();
        lastNameTxtBox.clear();
        rankCombo.valueProperty().set(null);
        scoutEmail.clear();
        scoutAwards.clear();
        scoutBadges.clear();
        scoutPosition.clear();
    }

    @Override
    public void loadInfo(Scout scoutToLoad) {
        firstNameTxtBox.setText(scoutToLoad.getFirstName());
        lastNameTxtBox.setText(scoutToLoad.getLastName());
        rankCombo.setValue(scoutToLoad.getRank());
        scoutPosition.setText(scoutToLoad.getPosition());
        scoutEmail.setText(scoutToLoad.getEmail());
    }
}
