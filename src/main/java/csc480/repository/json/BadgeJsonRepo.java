package csc480.repository.json;

import csc480.model.Badge;
import csc480.repository.mongo.BaseMongoRepo;
import java.util.ArrayList;

public class BadgeJsonRepo extends BaseMongoRepo implements csc480.repository.BadgeRepository {
    public BadgeJsonRepo(){

    }

    @Override
    public void updateBadge(Badge badge) {

    }

    @Override
    public ArrayList<Badge> findAll() {
        return null;
    }
}
