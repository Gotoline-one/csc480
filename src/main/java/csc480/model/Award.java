package csc480.model;

import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Award  {
    private String id;
    String awardName;
    boolean isKnowBased, isActBase;
    private String awardDescription;
    private Duration timeRequirement;
    private boolean complete;
    private ArrayList<Activity> activityList = new ArrayList<>();

    public Award() {
    }

    public Award(String name, List<Activity> scoutActivities) {
        this.awardName = name;
        this.complete = false;
        this.activityList = new ArrayList<>(scoutActivities);
    }

    public Award(String name, String description, boolean isCompleted, Duration timeReq, List<Activity> scoutActivities) {
        this.awardName = name;
        this.awardDescription = description;
        this.complete = isCompleted;
        this.timeRequirement =timeReq;
        this.activityList = new ArrayList<>(scoutActivities);
    }

    public String getAwardDescription() {
        return awardDescription;
    }

    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
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

    public boolean getComplete() {
        return complete;
    }

    public void setComplete() {
        this.complete = true;
    }

    public void setTitle(String text) {
        awardName = text;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setKnowledge(boolean selected) {
        isKnowBased = selected;
    }

    @Override
    public String toString() {
        return awardName;
    }

//    @Override
//    public @NotNull Iterator<Activity> iterator() {
//        return activityList.iterator();
//    }
    public Iterator<Activity> getActivities() {
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

    public void updateAward(Award updatedAward) {
        if (updatedAward == this) return;

        setActBase(updatedAward.isActBase);
        setKnowBased(updatedAward.isKnowBased);
        setAwardDescription(updatedAward.awardDescription);
        setTitle(updatedAward.awardName);
        activityList.clear();
        if (!activityAddAll(updatedAward.activityList)) {
            System.out.println("Issue updating Award Activities Award.updateAward()");
        }
    }


    public Duration getTimeRequirement() {
        return this.timeRequirement;
    }
    public void setTimeRequirement(Duration newTimeRequirement) {
       this.timeRequirement = newTimeRequirement;
    }

    public List<Activity> getActivityList() {
        return  Collections.unmodifiableList(activityList);

    }

    public String getId() {
        return "";
    }

    public void setId(String string) {

    }
}