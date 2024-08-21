package csc480.Branched;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Award {
    private String awardName;
    private String awardType;
    private Duration timeRequirement;
    private ScoutAction[] requiredScoutActions;
    private boolean complete;
    // Using ArrayList for dynamic resizing
    private List<ScoutAction> scoutActionList = new ArrayList<>();
    public Award(String name, ScoutAction[] scoutActions) {
        this.awardName = name;
        this.requiredScoutActions = scoutActions;
        this.complete = false;
        this.scoutActionList = new ArrayList<>(Arrays.asList(scoutActions));
    }
    public boolean getComplete() {
        return complete;
    }
    public void setComplete() {
        this.complete = true;
    }
}