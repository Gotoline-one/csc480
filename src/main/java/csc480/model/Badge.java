package csc480.model;

import csc480.model.Requirement;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.util.*;

public class Badge implements TreeNodeData{
    private String id;
    String description;
    String badgeRequirements;

    String name;
    boolean isKnowBased, isPhysical;
    private String badgeType;
    private Duration timeRequirement;
    private boolean complete;
   

    public ArrayList<Requirement> getBadgeRequirementsList() {
        return badgeRequirementsList;
    }

    public void setBadgeRequirementsList(ArrayList<Requirement> badgeRequirementsList) {
        this.badgeRequirementsList = badgeRequirementsList;
    }

    private ArrayList<Requirement> badgeRequirementsList;

    public Badge() {
    }


    public Badge(String name, String requirements, boolean completed) {
        this.name = name;
        this.badgeRequirements = requirements;
        this.complete = completed;
    }
    public Badge(String newID, String name, String requirements) {
        this.id = newID;
        this.name = name;
        this.description = requirements;
    }
    public Badge(String name, String description, boolean isKnowBased, boolean isPhysical, Duration timeRequirement, boolean complete, ArrayList<Requirement> requirementArrayList) {
        this.description = description;
        this.name = name;
        this.isKnowBased = isKnowBased;
        this.isPhysical = isPhysical;
        this.timeRequirement = timeRequirement;
        this.complete = complete;
        this.badgeRequirementsList.addAll(requirementArrayList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKnowBased() {
        return isKnowBased;
    }

    public void setKnowBased(boolean knowBased) {
        isKnowBased = knowBased;
    }

    public boolean getKnowBased() {
        return isKnowBased;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public void setPhysical(boolean physical) {
        isPhysical = physical;
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


    public Iterator<Requirement> getActivities() {
        return badgeRequirementsList.iterator();
    }

    public boolean RequirementAddAll(ArrayList<Requirement> newbadgeRequirementsList) {
        if (newbadgeRequirementsList == null) return false;

        this.badgeRequirementsList.addAll(newbadgeRequirementsList);
        return (newbadgeRequirementsList.size() == this.badgeRequirementsList.size());
    }

    public boolean RequirementAddAll(ObservableList<Requirement> newbadgeRequirementsList) {
        if (newbadgeRequirementsList == null) return false;
        badgeRequirementsList.clear();
        badgeRequirementsList.addAll(newbadgeRequirementsList);
        return true;
    }

    public boolean addRequirement(Requirement act) {
        return this.badgeRequirementsList.add(act);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(String badgeType) {
        this.badgeType = badgeType;
    }

    public Duration getTimeRequirement() {
        return timeRequirement;
    }

    public void setTimeRequirement(Duration timeRequirement) {
        this.timeRequirement = timeRequirement;
    }

    public List<Requirement> getbadgeRequirementsList() {
        return Collections.unmodifiableList(badgeRequirementsList);

    }

    public boolean getIsPhys() {
        return isPhysical;
    }

    public void updateBadge(Badge updatedBadge) {
        if (updatedBadge == this) return;

        setPhysical(updatedBadge.isPhysical);
        setKnowBased(updatedBadge.isKnowBased);
        setBadgeDescription(updatedBadge.description);
        setName(updatedBadge.name);
        badgeRequirementsList.clear();
        if (!RequirementAddAll(updatedBadge.badgeRequirementsList)) {
            System.out.println("Issue updating Award Activities Award.updateAward()");
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getBadgeRequirements() {
        return badgeRequirements;
    }

    public void setBadgeRequirements(String badgeRequirements) {
        this.badgeRequirements = badgeRequirements;
    }

    public String getId() {
        return id;
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


