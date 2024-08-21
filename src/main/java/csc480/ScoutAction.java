package csc480.Branched;


import java.time.Duration;

class ScoutAction {
    private String name;
    private Duration timeToComplete;
    private int amountComplete;
    private boolean isComplete;
    public ScoutAction(String name) {
        this.name = name;
        this.isComplete = false;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ScoutAction)) return false;
        ScoutAction scoutAction = (ScoutAction) obj;
        return name.equals(scoutAction.name);
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
}