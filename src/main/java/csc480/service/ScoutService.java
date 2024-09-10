package csc480.service;

import csc480.model.Scout;
import csc480.repository.ScoutRepository;
import csc480.repository.json.ScoutJsonRepo;
import csc480.repository.mongo.ScoutMongoRepo;

import java.util.ArrayList;

public class ScoutService {
    private ScoutRepository remoteRepository = new ScoutMongoRepo();
    private ScoutRepository localRepository = new ScoutJsonRepo();
    private boolean isConnected = true;

    public boolean updateScout(Scout scout) {
        try {
            if (isConnected) {
                remoteRepository.updateScout(scout);
                return true;
            } else {
                localRepository.updateScout(scout);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, updateScout locally.");
            localRepository.updateScout(scout);
            isConnected = false;
            return false;
        }
    }

    public boolean updateScouts(ArrayList<Scout> scouts) {
        try {
            if (isConnected) {
                remoteRepository.updateScouts(scouts);
                return true;
            } else {
                localRepository.updateScouts(scouts);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, updateScouts locally.");
            localRepository.updateScouts(scouts);
            isConnected = false;
            return false;
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
            System.out.println("Connection to MongoDB failed, findAll locally.");
            isConnected = false;
            return localRepository.findAll();
        }
    }

    public boolean addScout(Scout scout){
        try {
            if (isConnected) {
                remoteRepository.addScout(scout);
                return true;
            } else {
                localRepository.addScout(scout);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, addScout locally.");
            localRepository.addScout(scout);
            isConnected = false;
            return false;
        }
    }


    public boolean addScouts(ArrayList<Scout> scouts){
        try {
            if (isConnected) {
                remoteRepository.addScouts(scouts);
                return true;
            } else {
                localRepository.addScouts(scouts);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, addScout locally.");
            localRepository.addScouts(scouts);
            isConnected = false;
            return false;
        }
    }

    public void createScout(Scout newScout) {
    }

}