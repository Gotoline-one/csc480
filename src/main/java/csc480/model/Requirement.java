package csc480.model;

import java.util.ArrayList;
import java.util.List;

public class Requirement implements NodeData {
    private String ID;
    private String displayID;
    private String description;
    private final List<Requirement> subRequirements;
    private boolean allSubRequirementsNeeded; // true if all sub-requirements are required
    private boolean completed;

    public Requirement(String displayID, String description, boolean allSubRequirementsNeeded, boolean completed) {
        this.displayID = displayID;
        this.description = description;
        this.allSubRequirementsNeeded = allSubRequirementsNeeded;
        this.completed = completed;
        this.subRequirements = new ArrayList<>();
    }



    @Override
    public String getDisplayText() {
        return  description;
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
        return this.getDisplayID ()+" "+getDisplayText();
    }

    public String getDisplayID() {
        return displayID;
    }

    public void setDisplayID(String displayID) {
        this.displayID = displayID;
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