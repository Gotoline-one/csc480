package csc480.repository;

import csc480.model.Award;

import java.util.ArrayList;

public  interface AwardRepository {
    void updateAward(Award award);
    ArrayList<Award> findAll();

    void updateAwards(ArrayList<Award> awards);

    void addAward(Award award);

    void addAwards(ArrayList<Award> awards);
}
