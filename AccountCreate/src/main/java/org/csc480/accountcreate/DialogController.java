package org.csc480.accountcreate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


//package org.csc480.guidemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class DialogController {
    @FXML
    private Label testLabel;

    @FXML
    private TextField scoutFirstNameTxtBox;

    @FXML
    private Button cancelBtn;

    @FXML
    private ImageView scoutImage;

    @FXML
    private Button addBadgesBtn;

    @FXML
    private TextField scoutLN;

    @FXML
    private Button saveScoutBtn;

    @FXML
    private Button AddScoutPhotoBtn;

    @FXML
    private Button addAwardsBtn;

    @FXML
    private Button NextBtn;

    @FXML
    private TextField scoutPosition1;

    @FXML
    private TextField scoutRank;

    @FXML
    private TextField scoutPosition11;

    @FXML
    private TextField scoutEmail;

    @FXML
    private TextField scoutPosition;

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
    void nextScout(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void saveScout(ActionEvent event) {

    }

    void testForEmail(){
        testLabel.setText(scoutEmail.getText());

    }


}
