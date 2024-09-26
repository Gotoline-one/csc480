package csc480.model;

import csc480.app.RoadToEagle;

import java.util.ArrayList;
import java.util.List;

public class Scout {


    private String dbID;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private String rank;
//    private ArrayList<Award> awards;
    private ArrayList<Badge> meritBadges;
//    private Membership membership;



    /**
     * Full Scout constructor with every field
     * used for batch creation.
     * @param newId DB ID
     * @param newFirstName Scouts First Name
     * @param newLastname Scouts Last Name
     * @param newRank Scout's Rank
     * @param newPosition Scout's Position (default to Scout)
     * @param newEmail Proper email address
     * @param newAward List of Awards
     * @param newBadge List of Badges
     */
            /** when first created ID can be a placeholder until saved to db.
             * then the app can know what has and has not been saved to the remote db */
    public Scout(String newId, String newFirstName, String newLastname, String newRank, String newPosition, String newEmail,  List<Badge> newBadge) {
        this.dbID = newId;
        this.firstName  = newFirstName;
        this.lastName   = newLastname;
        this.rank       = newRank;
        this.position   = newPosition;
        this.email      = newEmail;
        meritBadges     = new ArrayList<>();
        meritBadges.addAll(newBadge);
//        membership      = new Membership();

    }

    /**
     *  <b>FOR FAKE DATA ONLY</b>
     * @param newFirstName asdf
     * @param newLastname asdf
     * @param newRank asdf
     * @param newPosition asdf
     * @param newEmail asdf
     */
// for fake data only
    public Scout(String newFirstName, String newLastname, String newRank, String newPosition, String newEmail) {
        this.firstName  = newFirstName;
        this.lastName   = newLastname;
        this.rank       = newRank;
        this.position   = newPosition;
        this.email      = newEmail;
        meritBadges = new ArrayList<>();
//        membership = new Membership();
    }

    public Scout() {
        this.firstName = "";
        this.lastName = "";
        this.rank = "";
        this.email="";
        meritBadges = new ArrayList<>();
//        membership = new Membership();
    }

    public String getDbID() {
        return dbID;
    }

    public void setDbID(String dbID) {
        this.dbID = dbID;
    }

    /**
     * Copy other scout object by value into this one
     *
     * @param newScout scout object that holds info to copy into this one
     */
    public void updateScout(Scout newScout){
        setEmail(newScout.email);
        setFirstName(newScout.firstName);
        setLastName(newScout.lastName);
        setRank(newScout.rank);
        setPosition(newScout.getPosition());
        //membership = new Membership();

    }

    public String getEmail() {
        return this.email;
    }


    public boolean setEmail(String email) {
//        System.out.println("Inside Scout.setEmail: "+email);

        if (email == null || RoadToEagle.isValidEmailAddress(email)) {
            System.out.println("BAD EMAIL ");
            return false;
        } else {
            this.email = email;
//            System.out.println("GOOD EMAIL ");
            return true;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRank() {
        return rank;
    }
//
//    //Need to look at updating GUI stuff
//    // will this propagate to the List
//    public void addAward(Award newAward) {
//        awards.add(newAward);
//    }
//
//    //Need to look at updating GUI stuff
//    // will this propagate to the List, or do we need to mess with the Observer instead?
//    public void removeAward(String byName, String current) {
//        for (int i = 0; i < awards.size(); i++) {
//            if (awards.get(i).awardName.equals(byName)) {
//                awards.remove(i);
//                return;
//            }
//        }
//    }

    public void addMeritBadge(Badge newBadge) {
        meritBadges.add(newBadge);
    }
//    public Award[] getAwards() {
//        return awards.toArray(new Award[0]);
//    }
    public ArrayList<Badge> getMeritBadges() {
        return meritBadges;
    }

    public void validateEmail() {
// Implement email validation logic here
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;

    }

    public void clearBadges() {
        meritBadges.clear();
    }

//    public void clearAwards() {
//        awards.clear();
//    }
}