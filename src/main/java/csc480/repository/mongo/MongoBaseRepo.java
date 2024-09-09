package csc480.repository.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import csc480.model.Activity;
import csc480.model.Award;
import csc480.model.Badge;
import csc480.model.Scout;
import javafx.util.Duration;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MongoBaseRepo implements AutoCloseable{

    public MongoClient mongoClient;
    protected MongoDatabase database;
    protected MongoClientSettings settings;
    protected  String scoutDatabase;
    public MongoBaseRepo() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        String uri = "mongodb://127.0.0.1:27017";
        settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        mongoClient = MongoClients.create(settings);
        System.out.print("Databases: ");
        for(String res :  mongoClient.listDatabaseNames()){
            System.out.print(res+" ");
        }
        System.out.print("\n");
        scoutDatabase = "TroopManagementApp";
        database = mongoClient.getDatabase(scoutDatabase);

        Bson command = new BsonDocument("ping", new BsonInt64(1));
        Document commandResult = database.runCommand(command);
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
        List<Document> awardDocs = new ArrayList<>();
        for (Badge badge : scout.getMeritBadges()) {
            badgeDocs.add(toDocument(badge));
        }
        for (Award award : scout.getAwards()) {
            awardDocs.add(toDocument(award));
        }
        return new Document("fname",    scout.getFirstName())
                .append("lname",        scout.getLastName())
                .append("email",        scout.getEmail())
                .append("rank",         scout.getRank())
                .append("position",     scout.getPosition())
                .append("awards",       awardDocs)
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
        List<Document> awardDocs = document.getList("awards", Document.class);
        for (Document awardDoc : awardDocs) {
            awards.add(fromAwardDocument(awardDoc));
        }
        return new Scout(document.getString("fname"),
                document.getString("lname"),
                document.getString("email"),
                document.getString("rank"),
                document.getString("position"),
                awards, badges);
    }

    /**
     * Converts a Award Object back into a MongoDB Document
     * @param  award Award object
     * @return Mongo DB Object for Award
     */
    protected Document toDocument(Award award) {
        List<Document> activityDocs = new ArrayList<>();
        for (Iterator<Activity> it = award.getActivities(); it.hasNext(); ) {
            Activity activity = it.next();
            activityDocs.add(toDocument(activity));
        }
        return new Document("name", award.getAwardName())
                .append("description",      award.getAwardDescription())
                .append("complete",         award.getComplete())
                .append("timeRequirement",  award.getTimeRequirement())
                .append("activities",       activityDocs
                );
    }

    /**
     *  Converts a MongoDB Document back into a Award object
     * @param document MongoDB Award document
     * @return Award object from Document
     */
    protected Award fromAwardDocument(Document document) {
        List<Activity> activities = new ArrayList<>();
        List<Document> activityDocs = document.getList("activities", Document.class);

        for (Document activityDoc : activityDocs) {
            activities.add(fromActDocument(activityDoc));
        }

        return new Award(document.getString("name"),
                document.getString("description"),
                document.getBoolean("complete"),
                new Duration( document.getInteger("timeRequirement")),
                activities);
    }

    /**
     * Converts a MongoDB Document back into an Activity object
     * @param document Activity document from MongoDB
     * @return Activity Object
     */
    protected Activity fromActDocument(Document document) {
        return new Activity(document.getString("name"),
                document.getString("description"),
                new Duration(
                        document.getInteger("timeRequirement")
                ),
                document.getInteger("amountComplete"),
                document.getBoolean("action"),
                document.getBoolean("knowledge"),
                document.getBoolean("complete")
        );
    }

    /**
     * Converts a MeritBadge object to a MongoDB Document
     * @param badge object
     * @return Mongo DB Document with Badge info
     */
    protected Document toDocument(Badge badge) {
        List<Document> activityDocs = new ArrayList<>();
        for (Iterator<Activity> it = badge.getActivities(); it.hasNext(); ) {
            Activity activity = it.next();
            activityDocs.add(toDocument(activity));
        }
        return new Document("name", badge.getBadgeName())
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

    /**
     * Converts a MongoDB Document back into a MeritBadge object
     * @param document MongoDB Document with Badge info
     * @return Badge object from Document
     */
    protected Badge fromBadgeDocument(Document document) {
        ArrayList<Activity> activities = new ArrayList<>();
        List<Document> activityDocs = document.getList("activities", Document.class);
        for (Document activityDoc : activityDocs) {
            activities.add(fromActDocument(activityDoc));
        }

        return new Badge(document.getString("name"),
                document.getString("description"),
                document.getBoolean("knowledge"),
                document.getBoolean("physical"),
                new Duration( document.getInteger("timeRequirement")),
                document.getBoolean("complete"),
                activities);
    }
}
