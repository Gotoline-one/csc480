package csc480.model;

import java.time.LocalDate;
import java.time.ZoneId;

class Membership {
    private String type;
    private boolean financialAssistance;
    private LocalDate membershipDate;
    private ZoneId timeZone;
    private String membershipStatus;
    public String getType() {
        return type;
    }
    public LocalDate getDate() {
        return membershipDate;
    }
    public ZoneId getTimeZone() {
        return timeZone;
    }
    public boolean getStatus() {
        return financialAssistance;
    }
    public void setAssistance(boolean needsAssistance) {
        this.financialAssistance = needsAssistance;
    }
    public void setStatus(String newStatus) {
        this.membershipStatus = newStatus;
    }
}