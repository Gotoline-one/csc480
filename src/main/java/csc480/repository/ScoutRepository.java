package csc480.repository;
import csc480.model.Scout;

import java.util.ArrayList;

public interface ScoutRepository {
    void updateScout(Scout scout);
    ArrayList<Scout> findAll();

    void updateScouts(ArrayList<Scout> scouts);
    void addScout(Scout scout);
    void addScouts(ArrayList<Scout> scouts);
}
