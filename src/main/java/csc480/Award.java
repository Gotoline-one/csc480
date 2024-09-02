package csc480;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Award implements Iterable<Activity>{
    String awardName;
    private String awardType;

    public String getAwardDescription() {
        return awardDescription;
    }

    public void setAwardDescription(String awardDescription) {
        this.awardDescription = awardDescription;
    }

    private String awardDescription;

    public boolean isKnowBased() {
        return isKnowBased;
    }

    public void setActBase(boolean actBase) {
        isActBase = actBase;
    }

    public boolean isActBase() {
        return isActBase;
    }

    public void setKnowBased(boolean knowBased) {
        isKnowBased = knowBased;
    }

    boolean isKnowBased, isActBase;

    private Duration timeRequirement;
//    private Activity[] requiredScoutActivities;
    private boolean complete;
    // Using ArrayList for dynamic resizing
    private List<Activity> activityList = new ArrayList<>();
    public Award(){ }


   public boolean activityAddAll(ArrayList<Activity> newActivityList)
   {
        if(newActivityList ==null)
            return false;

        this.activityList.addAll(newActivityList);
        return (newActivityList.size() == this.activityList.size());
   }

    public boolean activityAddAll(ObservableList<Activity> newActivityList)
    {
        if(newActivityList ==null) return false;
        activityList.clear();
        activityList.addAll(newActivityList);
        return true;
    }

    public boolean addActivity(Activity act){
        return this.activityList.add(act);

    }
    public Award(String name, Activity[] scoutActivities) {
        this.awardName = name;
//        this.requiredScoutActivities = scoutActivities;
        this.complete = false;
        this.activityList = new ArrayList<>(Arrays.asList(scoutActivities));
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
    }

    @Override
    public String toString(){
        return awardName;
    }

    @Override
    public @NotNull Iterator<Activity> iterator() {
        return activityList.iterator();
    }


    public void updateAward(Award currentAward) {
        setActBase(currentAward.isActBase);
        setKnowBased(currentAward.isKnowBased);
        setAwardDescription(currentAward.awardDescription);
        setTitle(currentAward.awardName);
        activityList.addAll(currentAward.activityList);

    }
}