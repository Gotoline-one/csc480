package csc480.repository.json;

import csc480.model.Badge;
import csc480.repository.mongo.MongoBaseRepo;
import java.util.ArrayList;

public class JsonBadgeRepo extends MongoBaseRepo implements csc480.repository.BadgeRepository {
    public JsonBadgeRepo(){

    }

    @Override
    public void updateBadge(Badge badge) {

    }

    @Override
    public ArrayList<Badge> findAll() {
        return null;
    }
}
