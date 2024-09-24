package csc480.model;

import javafx.util.Duration;

public class Activity {
    private String id;
    String name;
    String Description;
    private Duration timeToComplete;
    private int amountComplete;
    private boolean isComplete;

    public Activity(String name, String description, Duration timeToComplete, int amountComplete, boolean actionBased, boolean knowledgeBased, boolean isComplete) {
        this.name = name;
        Description = description;
        this.timeToComplete = timeToComplete;
        this.amountComplete = amountComplete;
        this.actionBased = actionBased;
        this.knowledgeBased = knowledgeBased;
        this.isComplete = isComplete;
    }


    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isKnowledgeBased() {
        return knowledgeBased;
    }

    public void setKnowledgeBased(boolean knowledgeBased) {
        this.knowledgeBased = knowledgeBased;
    }

    public boolean isActionBased() {
        return actionBased;
    }

    public void setActionBased(boolean actionBased) {
        this.actionBased = actionBased;
    }

    public int getAmountComplete() {
        return amountComplete;
    }

    public void setAmountComplete(int amountComplete) {
        this.amountComplete = amountComplete;
    }

    public Duration getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(Duration timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public void setDescription(String description) {
        Description = description;
    }

    boolean knowledgeBased;
    boolean actionBased;
    public Activity(String name) {
        this.name = name;
        this.isComplete = false;
    }


    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Activity)) return false;
        Activity activity = (Activity) obj;
        return name.equals(activity.name);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void complete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public int partiallyComplete(int completeness) {
        this.amountComplete += completeness;
        return this.amountComplete;
    }

    public int getPartialComplete() {
        return amountComplete;
    }

    public String getName(){ return this.name;}
    public String getDescription() {return Description;}

    @Override
    public String toString() {
        return name;
    }


}
