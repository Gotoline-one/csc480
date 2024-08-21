package csc480.Branched;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

class ScoutEvent {
    private LocalTime date;
    private LocalDate timeZone;
    private Address location;
    private String type;
    private ScoutAction[] availableScoutActions;
    private Award[] possibleAwards;
    private String log;
    // Using ArrayList for dynamic resizing
    private List<ScoutAction> scoutActionList = new ArrayList<>();
    private List<Award> awardList = new ArrayList<>();
    public LocalTime getDate() {
        return date;
    }
    public Address getLocation() {
        return location;
    }
    public String getType() {
        return type;
    }
    public void setDate(LocalTime newDate, ZoneId timeZone) {
        this.date = newDate;
// Set time zone if needed
    }
    public void setLocation(Address newAddress) {
        this.location = newAddress;
    }
    public void setLog(String newLog) {
        this.log = newLog;
    }
    public void addAction(ScoutAction newScoutAction) {
        scoutActionList.add(newScoutAction);
    }
    public void addAward(Award newAward) {
        awardList.add(newAward);
    }
}