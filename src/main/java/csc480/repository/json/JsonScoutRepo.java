package csc480.repository.json;

import csc480.model.Scout;
import csc480.repository.ScoutRepository;
import csc480.repository.mongo.MongoBaseRepo;

import java.util.List;

public class JsonScoutRepo extends MongoBaseRepo implements ScoutRepository {
    @Override
    public void updateScout(Scout scout) {

    }

    @Override
    public List<Scout> findAll() {
        return List.of();
    }
}