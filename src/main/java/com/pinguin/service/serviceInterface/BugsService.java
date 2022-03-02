package com.pinguin.service.serviceInterface;

import com.pinguin.entity.Bug;

import java.util.List;
import java.util.UUID;

public interface BugsService {
    List<Bug> getBugs(String title);
    Bug getBugById(UUID issueId);
    Bug createBug(Bug bug);
    Bug updateBug(Bug bug);
    void deleteBug(UUID issueId);
    void deleteAll();
}
