package csc480;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * TODO: Finish Awards, Badges, and Photo stuff
 * TODO: Make Position a dropdown (There is a finite amount of scouting positions)
 */
public class NewScoutController {

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
    private TextField scoutRank;


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

    private void clearInput() {
        firstNameTxtBox.clear();
        lastNameTxtBox.clear();
        scoutRank.clear();
        scoutEmail.clear();
        scoutAwards.clear();
        scoutBadges.clear();
    }

    public void loadScout(Scout scoutToLoad) {
        firstNameTxtBox.setText(scoutToLoad.getFirstName());
        lastNameTxtBox.setText(scoutToLoad.getLastName());
        scoutRank.setText(scoutToLoad.getRank());
        scoutPosition.setText(scoutToLoad.getPosition());
    }

    /**
     * TODO: add data verification
     * TODO: make sure all fields are filled
     * TODO: make sure all fields make sense (Email RFC Check)
     * TODO: Add some kind of RED outline on problem fields, AND statement on bottom of screen.
     *
     * @param event
     */
    @FXML
    void nextScout(ActionEvent event) {
        Scout newScout = new Scout(
                firstNameTxtBox.getText(),
                lastNameTxtBox.getText(),
                scoutRank.getText());

        VistaNavigator.getMainController().addScout(newScout);
        clearInput();
    }

    @FXML
    void cancel(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        clearInput();
    }

    @FXML
    void saveScout(ActionEvent event) {
        Scout s = new Scout(firstNameTxtBox.getText(), lastNameTxtBox.getText(), scoutRank.getText());
        VistaNavigator.getMainController().addScout(s);
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        clearInput();
    }


}
