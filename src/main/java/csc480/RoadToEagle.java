package csc480.Branched;


import java.util.ArrayList;
import java.util.List;

public class RoadToEagle {
    private Scout[] theScouts;
    private Scout[] theLeaders;
    private Award[] meritBadges;
    private Award[] scoutingAwards;
    private ScoutEvent[] plannedScoutEvents;
    private ScoutEvent[] completedScoutEvents;
    private Scout[] parents;
    // Using ArrayList for dynamic resizing
    private List<Scout> scoutList = new ArrayList<>();
    private List<Award> meritBadgeList = new ArrayList<>();
    private List<Award> scoutingAwardList = new ArrayList<>();
    private List<ScoutEvent> plannedScoutEventList = new ArrayList<>();
    private List<ScoutEvent> completedScoutEventList = new ArrayList<>();
    private List<Scout> parentList = new ArrayList<>();
    public ScoutAction createAction(String actionName, String meritBadge) {
        return new ScoutAction(actionName);
    }
    public void addScout(String firstName, String lastName, String email) {
        Scout newScout = new Scout(firstName, lastName, email);
        scoutList.add(newScout);
    }
    public Award createAward(String awardName, ScoutAction[] scoutActions) {
        Award newAward = new Award(awardName, scoutActions);
        if (scoutActions.length > 0 && scoutActions[0].name.contains("Merit Badge")) {
            meritBadgeList.add(newAward);
        } else {
            scoutingAwardList.add(newAward);
        }
        return newAward;
    }
}