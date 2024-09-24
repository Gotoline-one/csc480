package csc480.controller;


import csc480.app.RoadToEagle;
import csc480.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import java.util.ArrayList;

import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckTreeView;
//import org.controlsfx.control.CheckTreeItem;

/**
 */
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
    MainController mainController;

    @FXML
    TreeView<TreeNodeData> badgeTree;

    @FXML
    VBox treeHomePane;

    @FXML
    private ListView<Badge> scoutBadgeList;
    private ObservableList<Badge> badgeListObserver;

        @FXML
    void addScoutPhoto() {

    }

    @FXML
    void addBadges() {
        if (this.currentScout ==null  ) currentScout = new Scout();
        formToScout();

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
        // No previous scout was selected so make a new one to add to list
        if(this.currentScout ==null){
            this.currentScout = new Scout();
        }

        //Form is filled out correctly so put new scout on the main scout list and start on next one
        if( formToScout()) {
            if (mainController != null) {
                mainController.addScout(this.currentScout);
//            VistaNavigator.getMainController().addScout(this.currentScout);
                this.currentScout = null;
//                mainController.currentScoutSelected = null;// should be taken care of in maincontroller.addScout
                clearInfo();
                System.out.println("save scout and get new form");
//                if (mainController != null)
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
//        updateModelFromTree( (CheckBoxTreeItem<TreeNodeData>) badgeTree.getRoot() );

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

        TreeNodeCheckList rootCheckListItem = loadCheckList(this.currentScout.getMeritBadges());
        treeHomePane.setPadding(new Insets(1));
        buildUI(treeHomePane, rootCheckListItem,5);

    }

    private TreeNodeCheckList loadCheckList(ArrayList<Badge> meritBadges) {
        if(meritBadges ==null) return null;

        // create fake root that wont be displayed
        TreeNodeCheckList rootNode = new TreeNodeCheckList(null);

        for(Badge badge : meritBadges ){
            TreeNodeCheckList badgeNode = new TreeNodeCheckList(badge);
            for(Requirement req : badge.getBadgeRequirementsList()){
                badgeNode.addChild(new TreeNodeCheckList(req));
            }

            rootNode.addChild(badgeNode);
        }
        return rootNode;
    }

    // Method to build the UI recursively
    private void buildUI(VBox parentPane, TreeNodeCheckList checkListItem, double indent) {
        if(checkListItem.getTreeNodeData()  != null) {
            HBox hbox = createItemHBox(checkListItem, indent, parentPane);
            parentPane.getChildren().add(hbox);
        }

        if (!checkListItem.getChildren().isEmpty()) {
            for (TreeNodeCheckList child : checkListItem.getChildren()) {
                buildUI(parentPane, child, indent + 20); // Indent child items
            }
        }
    }

    // Method to create an HBox for an checkListItem
    private HBox createItemHBox(TreeNodeCheckList checkListItem, double indent, VBox parentPane) {
        HBox hbox = new HBox(5); // Spacing between checkbox and text
        hbox.setPadding(new Insets(0, 0, 0, indent));
        hbox.setFillHeight(true);
        hbox.getStyleClass().add("hbox");
        // Allow HBox to grow horizontally
        hbox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(hbox, Priority.ALWAYS);


        CheckBox checkBox = new CheckBox();
        Text text = new Text(' ' +checkListItem.getText());
        text.wrappingWidthProperty().bind(parentPane.widthProperty().subtract(indent + 40)); // Adjust 70 as needed
//        text..setPadding(new Insets(0, 0, 0, 0));
        checkBox.setPadding(new Insets(0, 0, 0, 0));
        // Bind the checkbox to the checkListItem's selected property
        checkBox.selectedProperty().bindBidirectional(checkListItem.selectedProperty());

        // Add listener to handle parent-child selection
        checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {

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
//                System.out.println("CLICKED");
//                if(checkListItem.getTreeNodeData() instanceof Requirement)
//                    parentPane.getChildren().remove(hbox);
            }

        });

        // Ensure the text expands to fill available space
        HBox.setHgrow(text, Priority.ALWAYS);

        hbox.getChildren().addAll(checkBox, text);

        return hbox;
    }


//    private void updateModelFromTree(CheckBoxTreeItem<TreeNodeData> item) {
//
//        // Copy checkbox into into Requirements & Badge
//        if(item !=null && item.getValue() != null) {
//            item.getValue().setCompleted(item.isSelected());
//        }
//            // Recursively process child items
//        for (TreeItem<TreeNodeData> child : item.getChildren()) {
//            if (child instanceof CheckBoxTreeItem) {
//                updateModelFromTree((CheckBoxTreeItem<TreeNodeData>) child);
//            }
//        }
//    }
//
//
//    public void loadScoutProgressView(ArrayList<Badge> badges) {
//        badgeTree.setCellFactory(CheckBoxTreeCell.forTreeView());
//
//        // Make hidden root so all badges appear at same level
//        CheckBoxTreeItem<TreeNodeData> rootItem = new CheckBoxTreeItem<>(null);
//        badgeTree.setShowRoot(false);
//
//        for(Badge badge : badges){
//            rootItem.getChildren().add(badgeToTreeItem(badge));
//        }
//
//        badgeTree.setRoot(rootItem);
//    }
//
//    private TreeItem<TreeNodeData> badgeToTreeItem(Badge badge){
//        CheckBoxTreeItem<TreeNodeData> reqList = new CheckBoxTreeItem<>(badge);
//        reqList.setSelected(reqList.getValue().getCompleted());
//        reqList.setExpanded(!reqList.getValue().getCompleted());
////        reqList.setExpanded(false);
//
//        for(Requirement req :   badge.getBadgeRequirementsList()){
//            CheckBoxTreeItem<TreeNodeData> reqCheckBox = createCheckBoxTreeItem(req);
//
//            reqCheckBox.setSelected(reqCheckBox.getValue().getCompleted());
//            reqCheckBox.setExpanded(!reqCheckBox.getValue().getCompleted());
////            reqList.setExpanded(false);
//
//            reqList.getChildren().add(reqCheckBox);
//        }
//
//        return reqList;
//    }
//
//
//    private CheckBoxTreeItem<TreeNodeData> createCheckBoxTreeItem(Requirement requirement) {
//        CheckBoxTreeItem<TreeNodeData> item = new CheckBoxTreeItem<>(requirement);
//        item.setExpanded(false);
//
//        // Set initial selected state
//        item.setSelected(requirement.getCompleted());
//
//        // Recursively add sub-requirements
//        if (requirement.getSubRequirements() != null) {
//            for (Requirement subReq : requirement.getSubRequirements()) {
//                CheckBoxTreeItem<TreeNodeData> subItem = createCheckBoxTreeItem(subReq);
//                item.getChildren().add(subItem);
//            }
//        }
//
//        return item;
//    }
}
