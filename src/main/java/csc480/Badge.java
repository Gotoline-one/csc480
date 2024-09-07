package csc480;

import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Badge implements Iterable<Activity> {
    String description;

    String badgeName;
    boolean isKnowBased, isActBase;
    private String badgeType;
    private String badgeDescription;
    private Duration timeRequirement;
    private boolean complete;
    private ArrayList<Activity> activityList = new ArrayList<>();

    Badge() {}

    public Badge(String newName, List<Activity> scoutActivities){
        this.badgeName = newName;
        this.complete=false;
        this.activityList = new ArrayList<>(scoutActivities);
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

    public boolean isActBase() {
        return isActBase;
    }

    public void setActBase(boolean actBase) {
        isActBase = actBase;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }

    @Override
    public String toString() {
        return badgeName;
    }

    @Override
    public @NotNull Iterator<Activity> iterator() {
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

    public void updateBadge(Badge updatedBadge) {
        if (updatedBadge == this) return;

        setActBase(updatedBadge.isActBase);
        setKnowBased(updatedBadge.isKnowBased);
        setBadgeDescription(updatedBadge.badgeDescription);
        setBadgeName(updatedBadge.badgeName);
        activityList.clear();
        if (!activityAddAll(updatedBadge.activityList)) {
            System.out.println("Issue updating Award Activities Award.updateAward()");
        }
    }

}

