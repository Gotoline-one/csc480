package csc480.controller;


import csc480.app.RoadToEagle;
import csc480.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class NewScoutController extends SubController<Scout>{

    @FXML
    public ImageView scoutImage;

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
    Scout currentScout;

    @FXML
    VBox treeHomePane;

    @FXML
    private ListView<Badge> scoutBadgeList;

    MainController mainController;
    private ObservableList<Badge> badgeListObserver;
    private SmartCheckList rootCheckListItem;

        @FXML
    void addScoutPhoto() {

    }

    @FXML
    void addBadges() {
        if (this.currentScout ==null  ) currentScout = new Scout();
//        formToScout();

        ScoutToBadgeController scoutToBadge;
        scoutToBadge = (ScoutToBadgeController) VistaNavigator.loadVista(VistaNavigator.SCOUT_BADGE);
        
            if(scoutToBadge != null) {
                scoutToBadge.loadInfo(this.currentScout);
            }
    }

    @FXML
    void initialize() {
        rankCombo.getItems().addAll("Tenderfoot","Second Class","First Class","Star","Life","Eagle");
        badgeListObserver = FXCollections.observableArrayList();
        scoutBadgeList.setItems(badgeListObserver);

        this.mainController = VistaNavigator.getMainController();
        if(mainController!=null) this.currentScout = mainController.currentScoutSelected;
        if(currentScout!=null)
            badgeListObserver.addAll(currentScout.getMeritBadges());
        //load up scout list


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
        else if(RoadToEagle.isValidEmailAddress(scoutEmail.getText()))
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
    void nextScout()
    {
            // create new scout object to store form data in

            this.currentScout = new Scout();
            for(SmartCheckList smartCheckList : rootCheckListItem.getChildren() ) {
                NodeData nodeData =  createBadgeFromChecklist(smartCheckList);
                currentScout.addMeritBadge((Badge) nodeData);

            }

        //Form is filled out correctly so put new scout on the main scout list and start on next one
        if( formToScout()) {
            if (mainController != null) {
                mainController.addScout(this.currentScout);
                this.currentScout = null;
                clearInfo();
                System.out.println("save scout and get new form");
                this.mainController.setLeftStatus("NewScoutController.NextScout()  Nor Form Errors");
            }
        }
        else
        { // Form was not filled out correctly, give user option to fix it
                // adds new scout to list, but has incomplete data
            mainController.addScout(this.currentScout);
            mainController.currentScoutSelected = this.currentScout;
        }



    }



     /**
     * This function is to Save data to current Scout
     *
     ****/
    @FXML
    boolean saveScout()
    {
        // no scout selected so make new one
        if(this.currentScout ==null)
            this.currentScout = new Scout();


        if(formToScout())
        { // formToScout sets this.currentScout
            if (mainController != null) {
                mainController.saveScout(this.currentScout);
                mainController.scoutList.refresh();
            }
        }else
        {// Issue with scout form - so don't modify Scout until fixed
            this.mainController.scoutList.getSelectionModel().clearSelection();
            this.mainController.scoutList.getSelectionModel().select(currentScout);
        }

        if(mainController !=null)
            this.mainController.setLeftStatus("NewScoutController.saveScout()");

        for(int index =0; index < currentScout.getMeritBadges().size(); index++){

            updateBadgeWithGui(currentScout.getMeritBadges().get(index),rootCheckListItem.getChildren().get(index));
        }

        return true;
    }




    /**
     * Clear out what is seen and the currentScout object
     *
     */
    @FXML
    void cancel() {
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
        scoutPosition.clear();
        treeHomePane.getChildren().clear();
    }

    /**
     * Scout to GUI
     * @param scoutToLoad:Scout
     */
    @Override
    public void loadInfo(Scout scoutToLoad) {
        if(scoutToLoad ==null) return;
        firstNameTxtBox.setText(scoutToLoad.getFirstName());
        lastNameTxtBox.setText(scoutToLoad.getLastName());
        rankCombo.setValue(scoutToLoad.getRank());
        scoutPosition.setText(scoutToLoad.getPosition());
        scoutEmail.setText(scoutToLoad.getEmail());
        this.currentScout = scoutToLoad;


        rootCheckListItem = loadCheckList(this.currentScout.getMeritBadges());

        if(rootCheckListItem !=null) {
            treeHomePane.setPadding(new Insets(1));
            buildUI(treeHomePane, rootCheckListItem, 5);
        }

    }

    /**
     *  Creates Checklist from Badges ArrayList
     *  returns null If null or Empty ArrayList is passed in
     * @param meritBadges ArrayList of Scout's Merit Badges
     * @return SmartCheckList
     */
    private SmartCheckList loadCheckList(ArrayList<Badge> meritBadges) {
        if(meritBadges ==null || meritBadges.isEmpty()) return null;

        // create hidden root
        SmartCheckList rootNode = new SmartCheckList(null);

        for(Badge badge : meritBadges ){
            SmartCheckList badgeNode = new SmartCheckList(badge);
            for(Requirement req : badge.getBadgeRequirementsList()){
                badgeNode.addChild(reqToChecklistItems(req));
            }

            rootNode.addChild(badgeNode);
        }
        return rootNode;
    }

    /**
     * Recursively builds TreeNodeChecklist from Requirement object and it sub-requirements
     * @param requirement: Requirement object to recursively create Checklist
     * @return return complete Checklist from Requirement
     */
    private SmartCheckList reqToChecklistItems(Requirement requirement){
        if (requirement ==null) return null;

        SmartCheckList checkListItem =  new SmartCheckList(requirement);

        if(requirement.getSubRequirements().isEmpty()){
            return checkListItem;
        }
        else{
            for(Requirement subReq : requirement.getSubRequirements()) {
                checkListItem.addChild(reqToChecklistItems(subReq));
            }
        }
        return checkListItem;
    }


    private NodeData  createBadgeFromChecklist( SmartCheckList smartChecklist){

        // this kicks us off
        if(smartChecklist.getTreeNodeData() instanceof Badge){
            Badge badge = new Badge();

            badge.setName(smartChecklist.getText());
            badge.setCompleted(smartChecklist.isSelected());
            badge.setDescription(smartChecklist.getDescription());

            Requirement newReqNode;
            for(SmartCheckList checkList : smartChecklist.getChildren()) {
                newReqNode = (Requirement) createBadgeFromChecklist(checkList);
                badge.addRequirement(newReqNode);
            }
            return badge;
        }
        else
        {
            Requirement newReqNode = new Requirement(smartChecklist.getDisplayID(), smartChecklist.getText(), true, smartChecklist.isSelected());
            newReqNode.setDescription(smartChecklist.getDescription());
            if(!smartChecklist.getChildren().isEmpty()){
                for(SmartCheckList checkList : smartChecklist.getChildren()){
                    newReqNode.addSubRequirement((Requirement) createBadgeFromChecklist(checkList) );
                }
            }
            return newReqNode;
        }
    }


    private void updateBadgeWithGui(NodeData nodeData, SmartCheckList smartChecklist){
        if(smartChecklist ==null || nodeData ==null) return;

        nodeData.setCompleted(smartChecklist.isSelected());

        ArrayList<Requirement>    nodeSubList;
        ArrayList<SmartCheckList> smartCheckArrayList;
        smartCheckArrayList = (ArrayList<SmartCheckList>) (smartChecklist.getChildren());

        // Badge or Requirement
        if(nodeData instanceof Badge)
            nodeSubList = ((Badge) nodeData).getBadgeRequirementsList();
        else
            nodeSubList = (ArrayList<Requirement>) ((Requirement) nodeData).getSubRequirements();

        //check for branch base case
        if(nodeSubList.isEmpty() || smartCheckArrayList.isEmpty()) {
            return;
        }

        // Recursive call
        for(int index = 0; index < smartCheckArrayList.size(); index ++){
            if(smartCheckArrayList.get(index).getChildren() !=null )
                updateBadgeWithGui(nodeSubList.get(index), smartCheckArrayList.get(index));
        }
    }


    /**
     * Recursively builds UI from checkListItem object
     * SmartCheckList items with null data elements are not displayed
     * @param parentPane VBox to attach object to.<br><b>If null returns with no action.</b>
     * @param checkListItem root of SmartCheckList to display. <br><b>If null returns with no action.</b>
     * @param indent indent of sub requirement level
     */
    private void buildUI(VBox parentPane, SmartCheckList checkListItem, double indent) {
        if(checkListItem ==null || parentPane ==null) return;

        if( checkListItem.getTreeNodeData()  != null) {
            HBox hbox = createItemHBox(checkListItem, indent, parentPane);
            parentPane.getChildren().add(hbox);
        }

        if (!checkListItem.getChildren().isEmpty()) {
            for (SmartCheckList child : checkListItem.getChildren()) {
                buildUI(parentPane, child, indent + 20); // Indent child items
            }
        }
    }

    // Method to create an HBox for an checkListItem
    private HBox createItemHBox(SmartCheckList checkListItem, double indent, VBox parentPane) {
        HBox hbox = new HBox(5); // Spacing between checkbox and text
        hbox.setPadding(new Insets(0, 0, 0, indent));
        hbox.setFillHeight(true);
        hbox.getStyleClass().add("hbox");
        // Allow HBox to grow horizontally
        hbox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(hbox, Priority.ALWAYS);


        CheckBox checkBox = new CheckBox();
        Text text = new Text(" "+checkListItem.getDisplayID() + " " +checkListItem.getText());
        text.wrappingWidthProperty().bind(parentPane.widthProperty().subtract(indent + 40)); // Adjust 70 as needed
        checkBox.setPadding(new Insets(0, 0, 0, 0));

        // Bind the checkbox to the checkListItem's selected property
        checkBox.selectedProperty().bindBidirectional(checkListItem.selectedProperty());

        hbox.setOnMouseClicked(new MouseEventEventHandler(hbox, checkListItem.getText(), parentPane));
        parentPane.setOnMouseClicked(new MouseEventEventHandler(hbox, checkListItem.getText(), parentPane));

        // Set initial visibility
        if (checkListItem.getTreeNodeData() instanceof Requirement) {
            if (checkListItem.isSelected()) {
                // Requirement is completed, hide the HBox
                hbox.setVisible(false);
                hbox.setManaged(false);
            } else {
                // Requirement is not completed, show the HBox
                hbox.setVisible(true);
                hbox.setManaged(true);
            }
        }

        checkBox.selectedProperty().addListener(new BooleanChangeListener(checkListItem, hbox));

        // Ensure the text expands to fill available space
        HBox.setHgrow(text, Priority.ALWAYS);

        hbox.getChildren().addAll(checkBox, text);

        return hbox;
    }

    private static HBox hiddenHBox;
    private static class MouseEventEventHandler implements EventHandler<MouseEvent> {
        private final HBox hbox;
        private final VBox root;
        private final String theText;

        public MouseEventEventHandler(HBox hbox, String theText, VBox parentPane) {
            this.root = parentPane;
            this.hbox = hbox;
            this.theText = theText;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    if (hbox !=null && mouseEvent.getSource() == hbox && hbox.isVisible()) {
                        hbox.setVisible(false);
                        hiddenHBox = hbox;
                        TextArea textarea = new TextArea(theText);
                        textarea.setWrapText(true);
                        textarea.setPrefHeight(hbox.getHeight());
                        int index = root.getChildren().indexOf(hbox);
                        root.getChildren().add(index,textarea);
                        System.out.println("double clicked " + mouseEvent.getSource().toString());
                        mouseEvent.consume();
                    }
                } else if (mouseEvent.getClickCount() >= 1 ){
                    if(hiddenHBox != null) {
                        hiddenHBox.setVisible(true);
                        root.getChildren().removeIf(node -> node instanceof TextArea);
                        hiddenHBox = null;
                        mouseEvent.consume();
                    }
                }
            }
        }
    }

    /**
     * Class to take care of Checkbox Listener
     */
    private static class BooleanChangeListener implements ChangeListener<Boolean> {
        private final SmartCheckList checkListItem;
        private final HBox hbox;

        public BooleanChangeListener(SmartCheckList checkListItem, HBox hbox) {
            this.checkListItem = checkListItem;
            this.hbox = hbox;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {

            // If the checkListItem has children, propagate the selection
            if (!checkListItem.getChildren().isEmpty()) {
                checkListItem.setChildrenSelected(isSelected);
            }

            // If the checkListItem has a parent and is deselected, deselect the parent if necessary
            if (checkListItem.getParent() != null && !isSelected) {
                checkListItem.setParentSelected(false);
            }

            // If the checkListItem is selected, check if all siblings are selected to update the parent
            if (checkListItem.getParent() != null && isSelected) {
                checkListItem.checkParentSelection();
            }

            // Hide or show the HBox based on the selected state
            if (checkListItem.getTreeNodeData() instanceof Requirement) {
                if (isSelected) {
                    // Requirement is completed, hide the HBox
                    hbox.setVisible(false);
                    hbox.setManaged(false);
                } else {
                    // Requirement is not completed, show the HBox
                    hbox.setVisible(true);
                    hbox.setManaged(true);
                }
            }

        }
    }



}
