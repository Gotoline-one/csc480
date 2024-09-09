package csc480.service;

import csc480.model.Scout;
import csc480.repository.ScoutRepository;
import csc480.repository.json.JsonScoutRepo;
import csc480.repository.mongo.MongoScoutRepo;

import java.util.ArrayList;

public class ScoutService {
    private ScoutRepository remoteRepository = new MongoScoutRepo();
    private ScoutRepository localRepository = new JsonScoutRepo();
    private boolean isConnected = true;

    public void updateScout(Scout scout) {
        try {
            if (isConnected) {
                remoteRepository.updateScout(scout);
            } else {
                localRepository.updateScout(scout);
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, saving locally.");
            localRepository.updateScout(scout);
            isConnected = false;
        }
    }

    public ArrayList<Scout> findAll(){
        try {
            if (isConnected) {
                return remoteRepository.findAll();
            } else {
                return localRepository.findAll();
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, saving locally.");
            isConnected = false;
            return localRepository.findAll();
        }



    }

}