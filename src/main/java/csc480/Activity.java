package csc480;

import javafx.util.Duration;

public class Activity {
    private final String name;
    private String Description;
    private Duration timeToComplete;
    private int amountComplete;
    private boolean isComplete;
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
