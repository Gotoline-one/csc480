package csc480.model;

public interface NodeData {
    void setDescription(String newDescription);
    String getDescription();
    String getDisplayText();
    void setDisplayID(String newId);
    String getDisplayID();
    void setCompleted(boolean isComplete);
    boolean getCompleted();


}
