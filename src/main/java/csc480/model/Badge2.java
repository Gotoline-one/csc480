package csc480.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import csc480.repository.json.ObjectIdDeserializer;
import org.bson.types.ObjectId;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class Badge2 {

    @JsonProperty("_id")
    private Map<String, String> id;  // Map to capture the nested $oid

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Requirements")
    private String requirements;

    // Getters and Setters
    public String getId() {
        return id.get("$oid");  // Get the value of $oid
    }

    public void setId(Map<String, String> id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
