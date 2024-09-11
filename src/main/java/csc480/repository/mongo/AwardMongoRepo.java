package csc480.repository.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import csc480.model.Award;
//import csc480.model.award;
import org.bson.Document;
import org.bson.types.ObjectId;

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

    @Override
    public void updateAwards(ArrayList<Award> awards) {
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> awardCollection = database.getCollection("awards");

            for (Award award : awards) {
                Document awardDoc = toDocument(award);

                if (award.getId() != null) {
                    // Update or insert (upsert) based on the _id
                    UpdateResult updateResult = awardCollection.updateOne(
                            Filters.eq("_id", new ObjectId(award.getId()) ),  // Filter by _id Obj
                            new Document("$set", awardDoc),    // Set new data
                            new UpdateOptions().upsert(true)   // Upsert if the document doesn't exist
                    );
                    System.out.println(updateResult);

                } else {
                    // If there's no _id, insert the document as a new entry
                    InsertOneResult result = awardCollection.insertOne(awardDoc);
                    award.setId(result.getInsertedId().asObjectId().getValue().toString());
                }
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAward(Award award) {

    }

    @Override
    public void addAwards(ArrayList<Award> awards) {

    }


}