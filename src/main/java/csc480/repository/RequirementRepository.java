package csc480.repository;

import csc480.model.Requirement;

import java.util.ArrayList;

public interface RequirementRepository {
    void updateRequirement(Requirement requirement);
    ArrayList<Requirement> findAll();
}
