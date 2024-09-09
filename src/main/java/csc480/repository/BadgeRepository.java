package csc480.repository;
import csc480.model.Badge;
import java.util.List;

public interface BadgeRepository {
    void updateBadge(Badge badge);
    List<Badge> findAll();
}
