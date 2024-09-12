package csc480.service;

import csc480.model.Award;
import csc480.repository.AwardRepository;
//import csc480.repository.json.AwardJsonRepo;
import csc480.repository.mongo.AwardMongoRepo;
import org.bson.BsonValue;

import java.util.ArrayList;
import java.util.Map;

public class AwardService {
    private AwardRepository remoteRepository = new AwardMongoRepo();
//    private AwardRepository localRepository = new AwardJsonRepo();
    private boolean isConnected = true;

    public boolean updateAward(Award Award) {
        try {
            if (isConnected) {
                remoteRepository.updateAward(Award);
                return true;
            } else {
//                localRepository.updateAward(Award);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, updateAward locally.");
//            localRepository.updateAward(Award);
            isConnected = false;
            return false;
        }
    }

    public boolean updateAwards(ArrayList<Award> Awards) {
        try {
            if (isConnected) {
                remoteRepository.updateAwards(Awards);
                return true;
            } else {
//                localRepository.updateAwards(Awards);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, updateAwards locally.");
//            localRepository.updateAwards(Awards);
            isConnected = false;
            return false;
        }
    }

    public ArrayList<Award> findAll(){
        try {
            if (isConnected) {
                return remoteRepository.findAll();
            } else {
                return null;
//                return localRepository.findAll();
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, findAll locally.");
            isConnected = false;
            return null; // localRepository.findAll();
        }
    }

    public boolean addAward(Award Award){
        try {
            if (isConnected) {
                remoteRepository.addAward(Award);
                return true;
            } else {
               // localRepository.addAward(Award);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, addAward locally.");
            //localRepository.addAward(Award);
            isConnected = false;
            return false;
        }
    }


    public  Map<Integer, BsonValue>  addAwards(ArrayList<Award> Awards){
        try {
            if (isConnected) {
                remoteRepository.addAwards(Awards);
                return null;
            } else {
              //  localRepository.addAwards(Awards);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, addAward locally.");
          //  localRepository.addAwards(Awards);
            isConnected = false;
            return null;
        }
    }

    public Map<Integer, BsonValue> createAward(Award newAward) {
        return null;
    }

}