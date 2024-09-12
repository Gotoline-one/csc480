package csc480.repository.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import csc480.model.Badge;
import org.bson.Document;
import java.util.ArrayList;

public class BadgeMongoRepo extends BaseMongoRepo implements csc480.repository.BadgeRepository {

    @Override
    public void updateBadge(Badge badge) {
        ArrayList<Badge> dbScouts = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> scoutCollection = database.getCollection("MeritBadge");
            scoutCollection.insertOne(toDocument(badge));
        }
    }

    @Override
    public ArrayList<Badge> findAll() {
        ArrayList<Badge> dbScouts = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> scoutCollection = database.getCollection("MeritBadge");
            FindIterable<Document> findIterable = scoutCollection.find(new Document());

            for (Document document : findIterable) {
                String badgeName = document.getString("badgeName");
                String requirements = document.getString("requirements");
                System.out.printf("%s %s \n", badgeName, requirements);
                System.out.println(document.toJson());
            }
        }

        return dbScouts;
    }
}