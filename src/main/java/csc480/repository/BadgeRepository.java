package csc480.repository;
import csc480.model.Badge;

import java.util.ArrayList;

public interface BadgeRepository {
    void updateBadge(Badge badge);
    ArrayList<Badge> findAll();
}
