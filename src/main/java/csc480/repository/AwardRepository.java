package csc480.repository;

import csc480.model.Award;

import java.util.ArrayList;

public  interface AwardRepository {
    void updateAward(Award award);
    ArrayList<Award> findAll();
}
