package csc480.model;

import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Badge  {
    String description;

    String badgeName;
    boolean isKnowBased, isPhysical;
    private String badgeType;
    private Duration timeRequirement;
    private boolean complete;
    private ArrayList<Activity> activityList = new ArrayList<>();

    public Badge() {}

    public Badge(String newName, List<Activity> scoutActivities){
        this.badgeName = newName;
        this.complete=false;
        this.activityList = new ArrayList<>(scoutActivities);
    }

    public Badge(String badgeName, String description, boolean isKnowBased, boolean isPhysical, Duration timeRequirement, boolean complete, ArrayList<Activity> activityList) {
        this.description = description;
        this.badgeName = badgeName;
        this.isKnowBased = isKnowBased;
        this.isPhysical = isPhysical;
        this.timeRequirement = timeRequirement;
        this.complete = complete;
        this.activityList = activityList;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public boolean isKnowBased() {
        return isKnowBased;
    }

    public void setKnowBased(boolean knowBased) {
        isKnowBased = knowBased;
    }

    public boolean getKnowBased() {
        return isKnowBased ;
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
        return badgeName;
    }


    public Iterator<Activity> getActivities(){
        return activityList.iterator();
    }

    public boolean activityAddAll(ArrayList<Activity> newActivityList) {
        if (newActivityList == null) return false;

        this.activityList.addAll(newActivityList);
        return (newActivityList.size() == this.activityList.size());
    }
    public boolean activityAddAll(ObservableList<Activity> newActivityList) {
        if (newActivityList == null) return false;
        activityList.clear();
        activityList.addAll(newActivityList);
        return true;
    }
    public boolean addActivity(Activity act) {
        return this.activityList.add(act);
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

    public List<Activity> getActivityList() {
        return  Collections.unmodifiableList(activityList);

    }

    public void setActivityList(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }
public boolean getIsPhys(){return isPhysical;}
    public void updateBadge(Badge updatedBadge) {
        if (updatedBadge == this) return;

        setPhysical(updatedBadge.isPhysical);
        setKnowBased(updatedBadge.isKnowBased);
        setBadgeDescription(updatedBadge.description);
        setBadgeName(updatedBadge.badgeName);
        activityList.clear();
        if (!activityAddAll(updatedBadge.activityList)) {
            System.out.println("Issue updating Award Activities Award.updateAward()");
        }
    }

}

