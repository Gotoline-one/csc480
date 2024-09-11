package csc480.service;

import csc480.model.Award;
import csc480.model.Badge;
import csc480.repository.BadgeRepository;
import csc480.repository.json.BadgeJsonRepo;
import csc480.repository.mongo.BadgeMongoRepo;

import java.util.ArrayList;

public class BadgeService {
    private final BadgeRepository remoteRepository = new BadgeMongoRepo();
    private final BadgeRepository localRepository = new BadgeJsonRepo();
    private boolean isConnected = true;

    public void updateBadge(Badge badge) {
        try {
            if (isConnected) {
                remoteRepository.updateBadge(badge);
            } else {
                localRepository.updateBadge(badge);
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, saving locally.");
            localRepository.updateBadge(badge);
            isConnected = false;
        }
    }

    public ArrayList<Badge> findAll(){
        try {
            if (isConnected) {
                return remoteRepository.findAll();
            } else {
                return localRepository.findAll();
//                localRepository.updateBadge(badge);
            }
        } catch (Exception e) {
            System.out.println("Connection to MongoDB failed, saving locally.");
            isConnected = false;
            return localRepository.findAll();
        }
    }

    public void updateBadges(ArrayList<Badge> updateBadge) {
    }
}