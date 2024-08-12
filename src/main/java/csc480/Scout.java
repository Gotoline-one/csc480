package csc480;

import java.util.ArrayList;

public class Scout {
    private String firstName;
    private String lastName;
    private String rank;
    private String position;
    private ArrayList<Award> meritBadges;
    private ArrayList<Award> awards;

    public Scout(String newFirstName, String newLastname, String newRank) {
        firstName = newFirstName;
        lastName = newLastname;
        rank = newRank;

    }
    public Scout() {
        firstName = "";
        lastName = "";
        rank = "";

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
}
