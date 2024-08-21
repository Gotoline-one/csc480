package csc480.Branched;


import java.util.ArrayList;
import java.util.List;

class Scout {
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private String ranks;
    private Award[] awards;
    private Award[] meritBadges;
    private Membership membership;
    // Using ArrayList for dynamic resizing
    private List<Award> awardList = new ArrayList<>();
    private List<Award> meritBadgeList = new ArrayList<>();
    public Scout(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public void setPosition(String newPosition) {
        this.position = newPosition;
    }
    public void addAward(Award newAward) {
        awardList.add(newAward);
    }
    public void removeAward(String byName, String current) {
        for (int i = 0; i < awardList.size(); i++) {
            if (awardList.get(i).awardName.equals(byName)) {
                awardList.remove(i);
                return;
            }
        }
    }
    public void addMeritBadge(Award newBadge) {
        meritBadgeList.add(newBadge);
    }
    public Award[] getAwards() {
        return awardList.toArray(new Award[0]);
    }
    public Award[] getMeritBadges() {
        return meritBadgeList.toArray(new Award[0]);
    }
    public void validateEmail() {
// Implement email validation logic here
    }
}
