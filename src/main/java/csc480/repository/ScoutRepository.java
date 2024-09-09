package csc480.repository;
import csc480.model.Scout;
import java.util.List;

public interface ScoutRepository {
    void updateScout(Scout scout);
    List<Scout> findAll();
}
