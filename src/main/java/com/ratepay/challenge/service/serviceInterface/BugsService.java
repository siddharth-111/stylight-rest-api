package com.ratepay.challenge.service.serviceInterface;

import com.ratepay.challenge.entity.Bug;

import java.util.List;
import java.util.UUID;

public interface BugsService {
    List<Bug> getBugs();
    Bug getBugById(UUID issueId);
    Bug createBug(Bug bug);
    Bug updateBug(Bug bug);
    void deleteBug(UUID issueId);
    void deleteAll();
}
