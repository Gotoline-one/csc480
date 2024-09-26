package csc480.repository.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import csc480.model.Badge;
import csc480.model.Scout;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;

public class Connection {

    protected  final String uri = "mongodb://127.0.0.1:27017";

    public String getUri() {
        return uri;
    }

    public String getScoutDatabase() {
        return scoutDatabase;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    protected  final String scoutDatabase = "TroopManagementApp";
    protected MongoDatabase database;
    public MongoClient mongoClient;
    private ServerApi serverApi;
    private  MongoClientSettings settings;

    public Connection() {
//        public static void main (String[]args){
        // Replace the placeholder with your Atlas csc480.csc480.repository.mongo.Connection string
        // Construct a ServerApi instance using the ServerApi.builder() method

        serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();

        if(!connect() ){
            //TODO: need to make some kind of "not connected" or "changes will not save" popup on error
            System.out.println("ERROR COULD NOT CONNECT TO DB");
        }

/*
//           // Create a new client and connect to the server
//            try (MongoClient mongoClient = MongoClients.create(settings)) {
//                MongoDatabase database = mongoClient.getDatabase("TroopManagementApp");
//                MongoCollection<Document> scoutCollection = database.getCollection("Scout");
//
//                MongoCollection<Document> meritBadgeCollection = database.getCollection("MeritBadge");
//                createMeritBadge(meritBadgeCollection);
//                createScout(scoutCollection);
//                createScout(scoutCollection);
//                deleteScout(scoutCollection);
//                updateEmail(scoutCollection);
//                updatePosition(scoutCollection);
//                updateRank(scoutCollection);
//                updateMeritBadge(scoutCollection);
//
//                // Retrieve a specific field from a document as text
//                Bson getRankRequirements = eq("rank", "Star");
//                for (Document docRank : database.getCollection("Rank").find(getRankRequirements)) {
//                    String jsonRank = docRank.toJson();
//                    ObjectMapper mapperRank = new ObjectMapper();
//                    JsonNode nodeRank = null;
//                    try {
//                        nodeRank = mapperRank.readTree(jsonRank);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//                    System.out.println(wrap(nodeRank.get("rank").asText()));
//                    System.out.println(wrap(nodeRank.get("reqs").asText()));
//                }
//
//                // Select a specific scout (document) from within the Scout collection and retrieve the requirements for a
//                // specific merit badge in that scout's document as text
//                Bson getMeritBadge = and(eq("firstName", "Tom"), eq("lastName", "Jones"));
//                for (Document docBadge : scoutCollection.find(getMeritBadge)) {
//                    String jsonBadge = docBadge.toJson();
//                    ObjectMapper mapperBadge = new ObjectMapper();
//                    JsonNode nodeBadge = null;
//                    try {
//                        nodeBadge = mapperBadge.readTree(jsonBadge);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    Bson getRequirements = eq("Name", nodeBadge.get("MeritBadge").asText());
//                    for (Document docReqs : database.getCollection("MeritBadge").find(getRequirements)) {
//                        String jsonReqs = docReqs.toJson();
//                        ObjectMapper mapperReqs = new ObjectMapper();
//                        JsonNode nodeReqs = null;
//                        try {
//                            nodeReqs = mapperReqs.readTree(jsonReqs);
//                        } catch (JsonProcessingException e) {
//                            throw new RuntimeException(e);
//                        }
//                        System.out.println(wrap(nodeReqs.get("Name").asText()));
//                        System.out.println(wrap(nodeReqs.get("Requirements").asText()));
//                    }
//                }
//
//                try {
//                    // Send a ping to confirm a successful Connection
//                    Bson command = new BsonDocument("ping", new BsonInt64(1));
//                    Document commandResult = database.runCommand(command);
//                    System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
//                } catch (MongoException me) {
//                    System.err.println(me);
//                }
//            }
 */
    }

    private boolean connect(){
        try(MongoClient mClient = MongoClients.create(settings)){
            database = mClient.getDatabase(scoutDatabase);

            // Send a ping to confirm a successful Connection
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);

//            System.out.println("You successfully connected to MongoDB!\n" + commandResult.toString());
        } catch (MongoException me) {
            System.err.println(me);
            return false;

        }
//        checkConnection();

        return true;
    }


    public boolean checkConnection(){
        try(MongoClient mClient = MongoClients.create(settings)){
            database = mClient.getDatabase(scoutDatabase);

            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);

//            System.out.println("You successfully connected to MongoDB!\n" + commandResult.toString());
        } catch (MongoException me) {
            System.err.println(me);
            return false;
        }
        return true;
    }

    /**
     * TODO:need to build other parts of the db to get this
     * @return arrayList of scouts
     *
     * UPDATED:
     *
     */
    public ArrayList<Scout> getScouts(){
        ArrayList<Scout> dbScouts = new ArrayList<>();
        ArrayList<Badge> dbBadges = new ArrayList<>();
        Scout currentScout;
        try(MongoClient mClient = MongoClients.create(settings)){
            database = mClient.getDatabase(scoutDatabase);
            MongoCollection<Document> scoutCollection = database.getCollection("Scout");
            FindIterable<Document> iterDoc = scoutCollection.find();
            FindIterable<Document> badgeDoc;
//            FindIterable<Document> awardDoc;
            for (Document document : iterDoc) {
                //Retrieve arraylist of meritbadges for each scout
                badgeDoc = scoutCollection.find(and(eq("firstName", getFirstName(document)), eq("lastName", getLastName(document)))).projection(include("MeritBadge"));
                for (Document doc : badgeDoc) {
                    dbBadges = getBadges(doc);
                }
                currentScout = new Scout(getId(document), getFirstName(document), getLastName(document), getRank(document), getPosition(document), getEmail(document), dbBadges);
                dbScouts.add(currentScout);
            }
        } catch (MongoException me) {
            System.err.println(me);
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return dbScouts;
    }

    private static String getId(Document scoutCollection) {
        String id = scoutCollection.toJson();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node.get("_id").asText();
    }

    private static String getFirstName(Document scoutCollection) {
        String firstName = scoutCollection.toJson();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(firstName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node.get("firstName").asText();
    }

    private static String getLastName(Document scoutCollection) {
        String lastName = scoutCollection.toJson();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(lastName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node.get("lastName").asText();
    }

    private static String getRank(Document scoutCollection) {
        String rank = scoutCollection.toJson();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(rank);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node.get("rank").asText();
    }

    private static String getPosition(Document scoutCollection) {
        String position = scoutCollection.toJson();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(position);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node.get("position").asText();
    }

    private static String getEmail(Document scoutCollection) {
        String email = scoutCollection.toJson();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(email);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node.get("email").asText();
    }

    //Parse JSON array into arraylist for merit badges
    @JsonDeserialize(as=Badge.class)
    private static ArrayList<Badge> getBadges(Document badgeCollection) throws JsonProcessingException {
        String newBadge = removeFieldName(badgeCollection.toJson());
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Badge> badgeList = mapper.readValue(newBadge, new TypeReference<ArrayList<Badge>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        return badgeList;
    }


    public void createMeritBadge(Badge newBadge){

        String badgeName = newBadge.getName();
        String requirements = "to hike";

        Document badge = new Document("_id", new ObjectId());
        badge.append("badgeName", badgeName)
                .append("requirements", requirements);
//        InsertOneResult res   = meritBadgeCollection.insertOne(badge);
    }

    public void  createScout(MongoCollection<Document> scoutCollection) {
        String fname = "Tom";
        String lname = "Jones";
        String email = "tomjones@email.com";
        String position = "Scout";
        String rank = "First Class";
        String meritBadge = "First Aid";

        Document scout = new Document("_id", new ObjectId());
        scout.append("firstName", fname)
                .append("lastName", lname)
                .append("email", email)
                .append("position", position)
                .append("rank", rank)
                .append("MeritBadge", meritBadge);
        InsertOneResult res   = scoutCollection.insertOne(scout);
    }


    public void deleteScout(MongoCollection<Document> scoutCollection) {
        String fname = "Tom";
        String lname = "Jones";

        Bson filter = and(eq("firstName", fname), eq("lastName", lname));
        DeleteResult deleteResult = scoutCollection.deleteOne(filter);
        System.out.println(deleteResult);
    }

    public void updateEmail(MongoCollection<Document> scoutCollection) {
        String fname = "Tom";
        String lname = "Jones";
        String email = "newtomjones@email.com";

        Bson filter = and(eq("firstName", fname), eq("lastName", lname));
        Bson updateOperation = set("email", email);
        UpdateResult updateResult = scoutCollection.updateOne(filter, updateOperation);
        System.out.println(updateResult);

    }

    public void updatePosition(MongoCollection<Document> scoutCollection) {
        String fname = "Tom";
        String lname = "Jones";
        String position = "New Position";

        Bson filter = and(eq("firstName", fname), eq("lastName", lname));
        Bson updateOperation = set("position", position);
        UpdateResult updateResult = scoutCollection.updateOne(filter, updateOperation);
        System.out.println(updateResult);

    }

    public void updateRank(MongoCollection<Document> scoutCollection) {
        String fname = "Tom";
        String lname = "Jones";
        String rank = "New Rank";

        Bson filter = and(eq("firstName", fname), eq("lastName", lname));
        Bson updateOperation = set("rank", rank);
        UpdateResult updateResult = scoutCollection.updateOne(filter, updateOperation);
        System.out.println(updateResult);

    }

    public void updateMeritBadge(MongoCollection<Document> scoutCollection) {
        String fname = "Tom";
        String lname = "Jones";
        String meritBadge = "New Merit Badge";

        Bson filter = and(eq("firstName", fname), eq("lastName", lname));
        Bson updateOperation = set("MeritBadge", meritBadge);
        UpdateResult updateResult = scoutCollection.updateOne(filter, updateOperation);
        System.out.println(updateResult);

    }

    // Remove field name from mongodb JSON in order to parse into arraylist
    public static final String removeFieldName(String removeFN) {
        String string = new String();
        String[] words = removeFN.split(" ");
        int count = 1;

        for (String word : words) {
            if(count > 3) {
                word = word.replaceFirst("\"MeritBadge\":", "");
                word = word.replaceFirst("\"Award\":", "");
                string += (word + " ");
            }
            count++;
        }
        return string.trim();
    }

    private static final String wrap(String toWrap) {
        String string = new String();
        String[] words = toWrap.split(" ");

        int currentLineLength = 0;
        for (String word : words) {

            // Wrap with \
            if(word.contains("\\")) {
                while(word.contains("\\"))
                    word = word.replaceFirst("\\\\", "\n");
                string += (word + " ");
                currentLineLength = 0;
                continue;
            }
            // Tab with /t
            if(word.contains("/t")) {
                while(word.contains("/t"))
                    word = word.replaceFirst("/t", "\t");
                string += (word + " ");
                continue;
            }
            //string += (word + " ");
            // Auto warping
            if(currentLineLength + word.length() + 1 <= 100) {
                string += (word + " ");
                currentLineLength += word.length() + 1;
            }
            else {
                string += ("\n" + word + " ");
                currentLineLength = word.length() + 1;
            }
        }
        return string.trim();
    }
}