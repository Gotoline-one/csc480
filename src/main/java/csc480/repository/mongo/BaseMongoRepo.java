package csc480.repository.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import csc480.model.*;
import javafx.util.Duration;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseMongoRepo implements AutoCloseable{

    public MongoClient mongoClient;
    protected MongoDatabase database;
    protected MongoClientSettings settings;
    protected  String scoutDatabase;
    public BaseMongoRepo() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        String uri = "mongodb://127.0.0.1:27017";
        settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();
        mongoClient = MongoClients.create(settings);
        scoutDatabase = "TroopManagementApp";
    }

    @Override
    public void close() throws Exception {

    }

    /**
     * Converts a Scout object to a MongoDB Document
     * @param scout Object to be turned into Document
     * @return Mongo Document
     */
    protected Document toDocument(Scout scout) {
        List<Document> badgeDocs = new ArrayList<>();
        for (Badge badge : scout.getMeritBadges()) {
            badgeDocs.add(toDocument(badge));
        }

        return new Document("fname",    scout.getFirstName())
                .append("lname",        scout.getLastName())
                .append("email",        scout.getEmail())
                .append("rank",         scout.getRank())
                .append("position",     scout.getPosition())
                .append("badges",       badgeDocs);
    }

    /**
     * Converts a MongoDB Document back into a Scout object
     * @param document object with Scout information
     * @return Scout Object from MongoDB Document
     */
    protected Scout scoutFromDocument(Document document) {
        ArrayList<Badge> badges = new ArrayList<>();
        List<Document> badgeDocs = document.getList("badges", Document.class);
        for (Document badgeDoc : badgeDocs) {
            badges.add(fromBadgeDocument(badgeDoc));
        }

        List<Award> awards = new ArrayList<>();

        return new Scout(document.getObjectId("_id").toString(),
                document.getString("fname"),
                document.getString("lname"),
                document.getString("email"),
                document.getString("rank"),
                document.getString("position"),
                awards, badges);
    }


    /**
     * Converts a MongoDB Document back into an Activity object
     * @param document Activity document from MongoDB
     * @return Activity Object
     */
    protected Requirement fromReqDocument(Document document) {
        return new Requirement(document.getString("name"),
                document.getString("description"),
                document.getBoolean("required"),
                document.getBoolean("completed")
        );


    }

    /**
     * Converts a MeritBadge object to a MongoDB Document
     * @param badge object
     * @return Mongo DB Document with Badge info
     */
    protected Document toDocument(Badge badge) {
        List<Document> activityDocs = new ArrayList<>();
//        for (Iterator<Activity> it = badge.getActivities(); it.hasNext(); ) {
//            Activity activity = it.next();
//            activityDocs.add(toDocument(activity));
//        }
        return new Document("name", badge.getName())
                .append("completed",        badge.getBadgeDescription())
                .append("activities",       activityDocs);
    }

    /**
     * Converts an Activity object to a MongoDB Document
     * @param activity object
     * @return MongoDB Document with Activiy info
     */
    protected Document toDocument(Activity activity) {
        return new Document("name",         activity.getName())
                .append("description",      activity.getDescription())
                .append("action",           activity.isActionBased())
                .append("knowledge",        activity.isKnowledgeBased())
                .append("amountComplete",   activity.getAmountComplete())
                .append("timeToComplete",   activity.getTimeToComplete())
                .append("completed",        activity.isComplete());
    }
    Document toDocument(Requirement requirement) {
        return new Document("name", requirement.getId())
                .append("requirementsString", requirement.getDescription());

    }

    /**
     * Converts a MongoDB Document back into a MeritBadge object
     * @param document MongoDB Document with Badge info
     * @return Badge object from Document
     */
    protected Badge fromBadgeDocument(Document document) {
        ArrayList<Requirement> requirements = new ArrayList<>();
        List<Document> requirementDocs = document.getList("activities", Document.class);
        for (Document requirementDoc : requirementDocs) {
            requirements.add(fromReqDocument(requirementDoc));
        }

        return new Badge(document.getString("name"),
                document.getString("description"),
                document.getBoolean("knowledge"),
                document.getBoolean("physical"),
                new Duration( document.getInteger("timeRequirement")),
                document.getBoolean("complete"),
                requirements);
    }
}
