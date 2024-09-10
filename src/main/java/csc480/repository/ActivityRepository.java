package csc480.repository;

import csc480.model.Activity;

import java.util.ArrayList;

public interface ActivityRepository {
    void updateActivity(Activity activity);
    ArrayList<Activity> findAll();
}
