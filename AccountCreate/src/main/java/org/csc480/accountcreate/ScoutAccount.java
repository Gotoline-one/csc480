package org.csc480.accountcreate;

public final class ScoutAccount {
    private Scout scout;
    private final static ScoutAccount INSTANCE = new ScoutAccount();

    private ScoutAccount() {}


    public static Object getInstance() {
        return INSTANCE;
    }

    public void setUser(Scout s) {
        this.scout = s;
    }

    public Scout getUser() {
        return this.scout;
    }
}
