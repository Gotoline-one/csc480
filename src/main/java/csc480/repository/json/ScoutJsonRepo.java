package csc480.repository.json;

import csc480.model.Scout;
import csc480.repository.ScoutRepository;
import csc480.repository.mongo.BaseMongoRepo;

import java.util.ArrayList;

public class ScoutJsonRepo extends BaseMongoRepo implements ScoutRepository {
    @Override
    public void updateScout(Scout scout) {

    }

    @Override
    public ArrayList<Scout> findAll() {
        return null;
    }

    @Override
    public void updateScouts(ArrayList<Scout> scouts) {

    }

    @Override
    public void addScout(Scout scout) {

    }

    @Override
    public void addScouts(ArrayList<Scout> scouts) {

    }
}
