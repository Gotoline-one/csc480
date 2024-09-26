package csc480.model;

import javafx.collections.ObservableList;

import java.util.*;

public class Badge implements NodeData {
    private String id;
    private String displayID = "";
    String description;
    String badgeRequirements;
    String name;
    private boolean complete;

    private ArrayList<Requirement> badgeRequirementsList;

    public Badge() {
        badgeRequirementsList = new ArrayList<>();
    }

    public Badge(String id, String name, String description,  boolean complete, String badgeRequirements, ArrayList<Requirement> requirementArrayList) {
        this.id = null;
        this.name = name;
        this.description = description;
        this.complete = complete;
        this.badgeRequirements = badgeRequirements;
        this.badgeRequirementsList = new ArrayList<>(requirementArrayList);
        this.displayID ="";
    }

    public ArrayList<Requirement> getBadgeRequirementsList() {
        return badgeRequirementsList;
    }

    public void setBadgeRequirementsList(ArrayList<Requirement> badgeRequirementsList) {
        this.badgeRequirementsList = badgeRequirementsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getBadgeDescription() {
        return description;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.description = badgeDescription;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean RequirementAddAll(ArrayList<Requirement> newBadgeRequirementsList) {
        if (newBadgeRequirementsList == null) return true;

        this.badgeRequirementsList.addAll(newBadgeRequirementsList);
        return (newBadgeRequirementsList.size() != this.badgeRequirementsList.size());
    }

    public boolean RequirementAddAll(ObservableList<Requirement> newbadgeRequirementsList) {
        if (newbadgeRequirementsList == null) return false;
        badgeRequirementsList.clear();
        badgeRequirementsList.addAll(newbadgeRequirementsList);
        return true;
    }

    public void addRequirement(Requirement act) {
        this.badgeRequirementsList.add(act);
    }

    public String getBadgeRequirements() {
        return badgeRequirements;
    }

    public void setBadgeRequirements(String badgeRequirements) {
        this.badgeRequirements = badgeRequirements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateBadge(Badge updatedBadge) {
        if (updatedBadge == this) return;
        setBadgeDescription(updatedBadge.description);
        setName(updatedBadge.name);
        badgeRequirementsList.clear();
        if (RequirementAddAll(updatedBadge.badgeRequirementsList)) {
            System.out.println("Issue updating Award Activities Award.updateAward()");
        }
    }

    public void setDisplayID(String displayID) {
        this.id = displayID;
    }

    public String getDisplayID() {
        return displayID;
    }

    @Override
    public String getDisplayText() {
        return name;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.complete = completed;
}
    @Override
    public boolean getCompleted() {
        return this.complete;
        }

    }


