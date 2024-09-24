package csc480.controller;

import csc480.model.Activity;
import csc480.model.Badge;
import csc480.model.Requirement;
import csc480.model.TreeNodeData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class NewBadgeController extends SubController<Badge> {

    ObservableList<Activity> availActivitiesListObserver;
    @FXML
    ListView<Activity> availActivitiesList;
    ObservableList<Activity> activitiesListObserver;
    ObservableList<Activity> selectedActivities;
    @FXML
    CheckBoxTreeItem<String> rootItem;
    @FXML
    TreeView<TreeNodeData> requirementsTree;
    @FXML
    StackPane treeHomePane;
    private MainController mainController;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField activityTitle;
    @FXML
    private CheckBox knowledgeCkBx;
    @FXML
    private CheckBox actionCkBx;
    @FXML
    private Badge currentBadge;
    @FXML
    private ListView<Activity> activitiesList;

    public void initialize() {
        this.mainController = VistaNavigator.getMainController();
        if (mainController == null) return;
        this.currentBadge = null;
        rootItem = new CheckBoxTreeItem<>("Requirements");
        rootItem.setExpanded(true);
    }


    /**
     *
     * @return if Badge object is complete
     */

    private boolean formToBadge() {
        if (this.currentBadge == null)
            currentBadge = new Badge();
        return true;
    }

    @FXML
    public void saveNewBtn(ActionEvent actionEvent) {
        currentBadge = new Badge();
        formToBadge();
        mainController.addBadge(this.currentBadge);
        this.currentBadge = null;
        clearInfo();
    }

    @Override
    public void clearInfo() {
        currentBadge = null;
    }


    @Override
    public void loadInfo(Badge newBadge) {
        if (newBadge == null)
            return;
        currentBadge = newBadge;
        activityTitle.setText(currentBadge.getName());
        loadBadgeRequirementsView(newBadge);
    }


    @FXML
    void cancelBtn(ActionEvent event) {
        VistaNavigator.loadVista(VistaNavigator.SPLASH);
        this.currentBadge = null;
        clearInfo();

        if (mainController != null) {
            mainController.currentScoutSelected = null;
            this.mainController.setLeftStatus("NewBadgeController.Cancel()");
        }
    }


    public void saveBtn(ActionEvent actionEvent) {
        if (currentBadge == null)
            currentBadge = new Badge();

        if (formToBadge()) {// the form is complete and no errors need to be shown to user
            if (mainController != null) {
                mainController.saveBadge(this.currentBadge);
                mainController.badgeList.refresh();
            }
        } else {
            if (mainController != null)
                this.mainController.setLeftStatus("issue with new Badge form saveBtn()");
        }
        if (mainController != null)
            this.mainController.setLeftStatus("NewBadgeController.saveBadge()");
    }

    public void loadBadgeRequirementsView(Badge badge) {
        TreeItem<TreeNodeData> rootItem = buildTree(badge);
        requirementsTree.setRoot(rootItem);
//        requirementsTree.setCellFactory(tree -> new TextOnlyTreeCell());

        requirementsTree.setShowRoot(false);

    }



    public TreeItem<TreeNodeData> buildTree(Badge badge) {
        TreeItem<TreeNodeData> badgeItem = new TreeItem<>(badge);
        badgeItem.setExpanded(true);

        for (Requirement req : badge.getBadgeRequirementsList()) {
            TreeItem<TreeNodeData> reqItem = createRequirementTreeItem(req);
            badgeItem.getChildren().add(reqItem);
        }

        return badgeItem;
    }

    private TreeItem<TreeNodeData> createRequirementTreeItem(Requirement requirement) {
        TreeItem<TreeNodeData> item = new TreeItem<>(requirement);

        if (requirement.getSubRequirements() != null) {
            for (Requirement subReq : requirement.getSubRequirements()) {
                TreeItem<TreeNodeData> subItem = createRequirementTreeItem(subReq);
                item.getChildren().add(subItem);
            }
        }

        return item;
    }

}