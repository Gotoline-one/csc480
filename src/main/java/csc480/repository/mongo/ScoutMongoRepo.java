package csc480.repository.mongo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import csc480.model.Scout;
import csc480.repository.ScoutRepository;
import org.bson.Document;

import javax.print.Doc;

public class ScoutMongoRepo extends BaseMongoRepo implements ScoutRepository {
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
            }
        }
        return dbScouts;
    }

    @Override
    public void updateScouts(ArrayList<Scout> scouts) {

    }

    @Override
    public void addScout(Scout scout) {

    }

    @Override
    public void addScouts(ArrayList<Scout> scouts) {
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("scouts");
            List<Document> scoutDocs = new ArrayList<>(List.of());
            for (Scout s : scouts) {
                scoutDocs.add(toDocument(s));
            }
            scoutCollection.insertMany(scoutDocs);

        }
    }
}
