package csc480.repository.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import csc480.model.Scout;
import csc480.repository.ScoutRepository;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoutMongoRepo extends BaseMongoRepo implements ScoutRepository {
    @Override
    public void updateScout(Scout scout) {
        ArrayList<Scout> dbScouts = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("scouts");
            scoutCollection.insertOne(toDocument(scout));
        }
    }

    @Override
    public ArrayList<Scout> findAll() {
        ArrayList<Scout> dbScouts = new ArrayList<>();
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("scouts");
            FindIterable<Document> findIterable = scoutCollection.find(new Document());

            for (Document document : findIterable) {
                dbScouts.add(scoutFromDocument(document));
            }
        }
        return dbScouts;
    }

    @Override
    public void updateScouts(ArrayList<Scout> scouts) {
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("scouts");


            for (Scout scout : scouts) {
                Document scoutDoc = toDocument(scout);

                if (scout.getId() != null) {
                    // Update or insert (upsert) based on the _id
                    UpdateResult updateResult = scoutCollection.updateOne(
                            Filters.eq("_id", new ObjectId(scout.getId()) ),  // Filter by _id Obj
                            new Document("$set", scoutDoc),    // Set new data
                            new UpdateOptions().upsert(true)   // Upsert if the document doesn't exist
                    );
            System.out.println(updateResult);

                } else {
                    // If there's no _id, insert the document as a new entry
                    InsertOneResult result = scoutCollection.insertOne(scoutDoc);
                    scout.setId(result.getInsertedId().asObjectId().getValue().toString());
                }
            }

        } catch (RuntimeException e) {
//            System.out.println(updateResult);
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    @Override
    public void addScout(Scout scout) {

    }

    /**
     * This function adds the ArrayList of Scout Objects to the MongoDb.<br>
     * On successful add each Scout Object is update with its Database ID
     *
     * @param scouts ArrayList of Scouts to be added to the database
     */
    @Override
    public void addScouts(ArrayList<Scout> scouts) {
        try (MongoClient mClient = MongoClients.create(settings)) {
            database = mClient.getDatabase(scoutDatabase);

            MongoCollection<Document> scoutCollection = database.getCollection("scouts");
            List<Document> scoutDocs = new ArrayList<>(List.of());

            for (Scout s : scouts) scoutDocs.add(toDocument(s));

            InsertManyResult ret = scoutCollection.insertMany(scoutDocs);
            Map<Integer, BsonValue> r = ret.getInsertedIds();


            for (int i = 0; i < r.size(); i++) {
                String scoutID = String.valueOf(r.get(i).asObjectId().getValue());
                scouts.get(i).setId(scoutID);
            }

        } catch (RuntimeException e) {

            throw new RuntimeException(e);
        }
    }
}
