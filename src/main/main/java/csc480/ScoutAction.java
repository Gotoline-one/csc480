package csc480;

public class ScoutAction {
    String title, description;
    Boolean knowledge, action;


    public Boolean getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Boolean knowledge) {
        this.knowledge = knowledge;
    }

    public Boolean getAction() {
        return action;
    }

    public void setAction(Boolean action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ScoutAction(String newTitle, String newDesc){
        title = newTitle;
        description = newDesc;

    }
}
