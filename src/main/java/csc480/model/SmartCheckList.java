package csc480.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

public class SmartCheckList {
    private NodeData nodeData;
    private String text;

    public String getDisplayID() {
        return displayID;
    }

    public void setDisplayID(String displayID) {
        this.displayID = displayID;
    }

    private String displayID;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private SmartCheckList parent;
    private final List<SmartCheckList> children = new ArrayList<>();

    public SmartCheckList(NodeData nodeData) {
        if (nodeData == null) return;
        this.nodeData = nodeData;
        this.displayID = nodeData.getDisplayID();
        this.text = nodeData.getDisplayText();
        this.setSelected(nodeData.getCompleted());
        this.description = nodeData.getDescription();
    }

    // Method to set the selected state of child items recursively
    static void setChildrenSelected(SmartCheckList checkListItem, boolean selected) {
        for (SmartCheckList child : checkListItem.getChildren()) {
            child.setSelected(selected);
            if (!child.getChildren().isEmpty()) {
                setChildrenSelected(child, selected);
                checkListItem.getTreeNodeData().setCompleted(selected);
            }
        }
    }

    // Method to deselect parent items if necessary
    static void setParentSelected(SmartCheckList parent, boolean selected) {
        if (!selected) {
            parent.setSelected(false);
            if (parent.getParent() != null) {
                setParentSelected(parent.getParent(), false);
            }
        }
    }

    // Method to check parent selection based on children
    static void checkParentSelection(SmartCheckList parent) {
        boolean allSelected = true;
        for (SmartCheckList child : parent.getChildren()) {
            if (!child.isSelected()) {
                allSelected = false;
                break;
            }
        }
        if (allSelected) {
            parent.setSelected(true);
            if (parent.getParent() != null) {
                checkParentSelection(parent.getParent());
            }
        }
    }

    public NodeData getTreeNodeData() {
        return nodeData;
    }

    public void addChild(SmartCheckList child) {
        child.setParent(this);
        children.add(child);
    }

    public String getText() {
        return text;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public List<SmartCheckList> getChildren() {
        return children;
    }

    public SmartCheckList getParent() {
        return parent;
    }

    public void setParent(SmartCheckList parent) {
        this.parent = parent;
    }


    // Method to set the selected state of child items recursively
    public void setChildrenSelected(boolean selected) {
        for (SmartCheckList child : this.getChildren()) {
            child.setSelected(selected);
            if (!child.getChildren().isEmpty()) {
                setChildrenSelected(child, selected);
            }
        }
    }

    public void setParentSelected(boolean isSelected) {
        if (!isSelected) {
            this.parent.setSelected(false);
            if (this.parent.getParent() != null) {
                setParentSelected(parent.getParent(), false);
            }
        }
    }

    public void checkParentSelection() {
        boolean allSelected = true;
        for (SmartCheckList child : this.parent.getChildren()) {
            if (!child.isSelected()) {
                allSelected = false;
                break;
            }
        }

        if (allSelected) {
            this.parent.setSelected(true);
            if (this.parent.getParent() != null) {
                checkParentSelection(this.parent.getParent());
            }
        }
    }
}
