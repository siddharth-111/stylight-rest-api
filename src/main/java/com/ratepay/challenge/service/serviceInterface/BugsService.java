package com.ratepay.challenge.service.serviceInterface;

import com.ratepay.challenge.dao.BugDAO;
import com.ratepay.challenge.model.Bug;

import java.util.List;
import java.util.UUID;

public interface BugsService {
    List<BugDAO> getBugs();
    Bug getBugById(UUID issueId);
    Bug createBug(Bug bug);
    Bug updateBug(Bug bug);
    void deleteBug(UUID issueId);
}
