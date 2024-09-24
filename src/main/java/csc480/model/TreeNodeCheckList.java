package csc480.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeCheckList {
    private TreeNodeData treeNodeData;
    private String text;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private TreeNodeCheckList parent;
    private final List<TreeNodeCheckList> children = new ArrayList<>();

    public TreeNodeCheckList(TreeNodeData treeNodeData) {
        if (treeNodeData == null) return;
        this.treeNodeData = treeNodeData;
        this.text = this.treeNodeData.getDisplayText();
        this.setSelected(treeNodeData.getCompleted());

    }

    // Method to set the selected state of child items recursively
    static void setChildrenSelected(TreeNodeCheckList checkListItem, boolean selected) {
        for (TreeNodeCheckList child : checkListItem.getChildren()) {
            child.setSelected(selected);
            if (!child.getChildren().isEmpty()) {
                setChildrenSelected(child, selected);
            }
        }
    }

    // Method to deselect parent items if necessary
    static void setParentSelected(TreeNodeCheckList parent, boolean selected) {
        if (!selected) {
            parent.setSelected(false);
            if (parent.getParent() != null) {
                setParentSelected(parent.getParent(), false);
            }
        }
    }

    // Method to check parent selection based on children
    static void checkParentSelection(TreeNodeCheckList parent) {
        boolean allSelected = true;
        for (TreeNodeCheckList child : parent.getChildren()) {
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

    public TreeNodeData getTreeNodeData() {
        return treeNodeData;
    }

    public void addChild(TreeNodeCheckList child) {
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

    public List<TreeNodeCheckList> getChildren() {
        return children;
    }

    public TreeNodeCheckList getParent() {
        return parent;
    }

    public void setParent(TreeNodeCheckList parent) {
        this.parent = parent;
    }

    // Method to set the selected state of child items recursively
    public void setChildrenSelected(boolean selected) {
        for (TreeNodeCheckList child : this.getChildren()) {
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
        for (TreeNodeCheckList child : this.parent.getChildren()) {
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
