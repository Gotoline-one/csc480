package csc480.repository.mongo;
import java.util.ArrayList;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import csc480.model.Scout;
import org.bson.Document;

public class MongoScoutRepo extends MongoBaseRepo implements csc480.repository.ScoutRepository {
    @Override
    public void updateScout(Scout scout) {
        ArrayList<Scout> dbScouts = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("MeritBadge");
            scoutCollection.insertOne(toDocument(scout));
        }
    }
    @Override
    public ArrayList<Scout> findAll() {
        ArrayList<Scout> dbScouts = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("MeritScout");
            FindIterable<Document> findIterable = scoutCollection.find(new Document());

            for (Document document : findIterable) {
                String scoutName = document.getString("scoutName");
                String requirements = document.getString("requirements");
                System.out.printf("%s %s \n", scoutName, requirements);
                System.out.println(document.toJson());
            }
        }
        return dbScouts;
    }
}
