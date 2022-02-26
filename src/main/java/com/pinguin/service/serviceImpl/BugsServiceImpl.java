package com.pinguin.service.serviceImpl;

import com.pinguin.service.serviceInterface.BugsService;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.entity.Bug;
import com.pinguin.repository.BugsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BugsServiceImpl implements BugsService {
    private final BugsRepository bugsRepository;

    private final ModelMapper modelMapper;

    public List<Bug> getBugs() {

        List<Bug> bugList = bugsRepository.findAll();

        return bugList;
    }

    public Bug getBugById(UUID issueId) {
        Bug bug = bugsRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found issue with id = " + issueId));

        return bug;
    }

    public Bug createBug(Bug bug) {

        Bug bugResponse = bugsRepository.save(bug);

        return bugResponse;
    }

    public Bug updateBug(Bug bug) {

        bugsRepository.findById(bug.getIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found issue with id = " + bug.getIssueId()));

        Bug bugResponse = bugsRepository.save(bug);

        return bugResponse;
    }

    public void deleteBug(UUID issueId) {

        bugsRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found issue with id = " + issueId));

        bugsRepository.deleteById(issueId);

    }

    public void deleteAll() {
        bugsRepository.deleteAll();
    }
}
