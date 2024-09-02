package csc480;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private TextField lastNameTxtBox;


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


    Scout currentScout;
    MainController mainController;


    @FXML
    void initialize() {
        rankCombo.getItems().addAll("Tenderfoot","Second Class","First Class","Star","Life","Eagle");
        this.currentScout = null;
        this.mainController = VistaNavigator.getMainController();
        System.out.println("NewScoutController  Initialize");

    }

    /**
     * formToScout works on of this.currentScout
     * if currentScout is null, then creates a new scout and fills it up
     * if currentScout is valid, it modifies currentScout with what is in the form
     *
     * @return Returns if form is complete,
     * and this.currentScout reflects form
     *
     */
    private boolean formToScout(){
            // check if we are updating or creating new scout obj
        if (this.currentScout ==null){
            this.currentScout = new Scout();
        }

        boolean fnIsValid    = false,
                lnIsValid    = false,
                rankIsValid  = false,
                posIsValid   = false,
                emailIsValid = false;


        String errorString = "";

        //check if gui is empty, then if info is valid.
        if(firstNameTxtBox.getText().isEmpty()) {
            firstNameTxtBox.setStyle("-fx-text-box-border: red;\n-fx-focus-color: red ;");
            errorString = errorString.concat(" First Name");
        }else{
            lnIsValid=true;
            firstNameTxtBox.setStyle("-fx-text-box-border: blue;\n-fx-focus-color: blue ;");
            this.currentScout.setFirstName(firstNameTxtBox.getText());
        }


        if(lastNameTxtBox.getText().isEmpty()) {
            lastNameTxtBox.setStyle("-fx-text-box-border: red;\n-fx-focus-color: red ;");
            errorString = errorString.concat(" Last Name");
        }else{
            fnIsValid=true;
            lastNameTxtBox.setStyle("-fx-text-box-border: blue;\n-fx-focus-color: blue ;");
            this.currentScout.setLastName(lastNameTxtBox.getText());
        }

        if(rankCombo.getValue() ==null) {
            rankCombo.setStyle("-fx-border-color: #ff0000;");
            errorString = errorString.concat(" Scout Rank ");
        }else{
            rankIsValid=true;
            rankCombo.setStyle("-fx-border-color: blue;");
            this.currentScout.setRank(rankCombo.getValue());
        }

        if(scoutPosition.getText().isEmpty()) {
            scoutPosition.setStyle("-fx-text-box-border: red;\n-fx-focus-color: red ;");
            errorString = errorString.concat(" Scout Position ");
        }else{
            posIsValid=true;
            scoutPosition.setStyle("-fx-text-box-border: blue;\n-fx-focus-color: blue ;");
            this.currentScout.setPosition(scoutPosition.getText());
        }

        //email needs more nuance because the text itself can be invalid
        if(scoutEmail.getText().isEmpty())
        {
            scoutEmail.setStyle("-fx-text-box-border: red;\n-fx-focus-color: red ;");
            errorString = errorString.concat("  email ");
        }
        else if(!RoadToEagle.isValidEmailAddress(scoutEmail.getText()))
        {
            scoutEmail.setStyle("-fx-text-box-border: red;\n-fx-focus-color: red ;");
            errorString = errorString.concat(" valid email ");
        }
        else
        {
            emailIsValid=true;
            scoutEmail.setStyle("-fx-text-box-border: blue;\n-fx-focus-color: blue ;");
            this.currentScout.setEmail(scoutEmail.getText());
        }

        //If there is any kind of issue then issue warning
        if(!(fnIsValid & lnIsValid & rankIsValid & posIsValid & emailIsValid)) {
            RoadToEagle.showAlert(Alert.AlertType.WARNING, "Form Error!", "Please enter " + errorString);
        }

        this.mainController.setLeftStatus("formToScout "+errorString);
        return ((fnIsValid & lnIsValid & rankIsValid & posIsValid & emailIsValid));

    }

    /**
     * Create new Scout from what is on the form and add it to the main scout list
     *
     */
    @FXML
    void nextScout(ActionEvent event)
    {
        // No previous scout was selected so make a new one to add to list
        if(this.currentScout !=null){
            this.currentScout = new Scout();
        }

        //Form is filled out correctly so put new scout on the main scout list and start on next one
        if( formToScout())
        {
            VistaNavigator.getMainController().addScout(this.currentScout);
            this.currentScout = null;
            VistaNavigator.getMainController().currentScoutSelected = null;
            clearInfo();
            System.out.println("save scout and get new form");
            if(mainController !=null)
                this.mainController.setLeftStatus("NewScoutController.NextScout()  Nor Form Errors");}
        else
        { // Form was not filled out correctly, give user option to fix it
                // adds new scout to list, but has incomplete data
            VistaNavigator.getMainController().addScout(this.currentScout);
            VistaNavigator.getMainController().currentScoutSelected = this.currentScout;
        }

    }


     /**
     * This function is to Save data to current Scout
     *
     ****/
    @FXML
    boolean saveScout(ActionEvent event)
    {
        // no scout selected so make new one
        if(this.currentScout ==null)
            this.currentScout = new Scout();


        if(formToScout()) { // formToScout sets this.currentScout
            if (mainController != null) {
                mainController.saveScout(this.currentScout);
                mainController.scoutList.refresh();
            }
        }else{// Issue with scout form - so don't modify Scout until fixed
            this.mainController.scoutList.getSelectionModel().clearSelection();
            this.mainController.scoutList.getSelectionModel().select(currentScout);
        }

        if(mainController !=null)
            this.mainController.setLeftStatus("NewScoutController.saveScout()");
        return true;
    }

    /**
     * Clear out what is seen and the currentScout object
     *
     */
    @FXML
    void cancel(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        this.currentScout = null;
        clearInfo();
        if(mainController !=null)
            this.mainController.setLeftStatus("NewScoutController.Canceled()");

    }
    /**
     * Clear Form
     */
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

    /**
     * Scout to Form
     * TODO: Awards and Badges
     * @param scoutToLoad:Scout
     */
    @Override
    public void loadInfo(Scout scoutToLoad) {
        firstNameTxtBox.setText(scoutToLoad.getFirstName());
        lastNameTxtBox.setText(scoutToLoad.getLastName());
        rankCombo.setValue(scoutToLoad.getRank());
        scoutPosition.setText(scoutToLoad.getPosition());
        scoutEmail.setText(scoutToLoad.getEmail());
        this.currentScout = scoutToLoad;

    }
}
