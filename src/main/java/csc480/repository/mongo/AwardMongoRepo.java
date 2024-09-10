package csc480.repository.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import csc480.model.Award;
import org.bson.Document;
import java.util.ArrayList;

public class AwardMongoRepo extends BaseMongoRepo implements csc480.repository.AwardRepository {

    @Override
    public void updateAward(Award award) {
        try (MongoClient mClient = MongoClients.create(settings))
        {
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> awardCollection = database.getCollection("Award");
            awardCollection.insertOne(toDocument(award));
        }
    }


    @Override
    public ArrayList<Award> findAll() {
        ArrayList<Award> dbAward = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> awardCollection = database.getCollection("Award");
            FindIterable<Document> findIterable = awardCollection.find(new Document());

            for (Document document : findIterable) {
                String awardName = document.getString("awardName");
                String requirements = document.getString("requirements");
                System.out.printf("%s %s \n", awardName, requirements);
                System.out.println(document.toJson());
            }
        }

        return dbAward;
    }





}