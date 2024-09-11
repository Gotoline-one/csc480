package csc480.repository.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import csc480.model.Activity;
import org.bson.Document;

import java.util.ArrayList;

public class ActivityMongoRepo extends BaseMongoRepo implements csc480.repository.ActivityRepository {

    @Override
    public void updateActivity(Activity activity) {
        try (MongoClient mClient = MongoClients.create(settings))
        {
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> activityCollection = database.getCollection("Activity");
            activityCollection.insertOne(toDocument(activity));
        }
    }

    @Override
    public ArrayList<Activity> findAll() {
        ArrayList<Activity> dbActivity = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> activityCollection = database.getCollection("Activity");
            FindIterable<Document> findIterable = activityCollection.find(new Document());

            for (Document document : findIterable) {
                String activityName = document.getString("activityName");
                String requirements = document.getString("requirements");
                System.out.printf("%s %s \n", activityName, requirements);
                System.out.println(document.toJson());
            }
        }
        return dbActivity;
    }





}