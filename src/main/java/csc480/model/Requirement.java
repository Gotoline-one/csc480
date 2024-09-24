package csc480.model;

import javafx.beans.property.SimpleBooleanProperty;
import java.util.ArrayList;
import java.util.List;

public class Requirement implements TreeNodeData {
    private String id;
    private String description;
    private List<Requirement> subRequirements;
    private boolean allSubRequirementsNeeded; // true if all sub-requirements are required
    private boolean completed;
    public Requirement(String id, String description, boolean allSubRequirementsNeeded, boolean completed) {
        this.id = id;
        this.description = description;
        this.allSubRequirementsNeeded = allSubRequirementsNeeded;
        this.completed = completed;
        this.subRequirements = new ArrayList<>();
    }

    @Override
    public String getDisplayText() {
        return id + ". " + description;
    }


    @Override
    public void setCompleted(boolean isSelected) {
        this.completed = isSelected;
    }

    @Override
    public boolean getCompleted() {
        return this.completed;
                }


    @Override
    public String toString(){
        return getDisplayText();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Requirement> getSubRequirements() {
        return subRequirements;
    }

    public void setSubRequirements(List<Requirement> subRequirements) {
        this.subRequirements = subRequirements;
    }

    public boolean isAllSubRequirementsNeeded() {
        return allSubRequirementsNeeded;
    }

    public void setAllSubRequirementsNeeded(boolean allSubRequirementsNeeded) {
        this.allSubRequirementsNeeded = allSubRequirementsNeeded;
    }

    public void addSubRequirement(Requirement req) {
        this.subRequirements.add(req);
    }

    public void appendDescription(String s) {
        description = description + s;
    }


}