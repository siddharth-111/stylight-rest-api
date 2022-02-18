package com.ratepay.challenge.service.serviceImpl;

import com.ratepay.challenge.dao.BugDAO;
import com.ratepay.challenge.exception.ResourceNotFoundException;
import com.ratepay.challenge.model.Bug;
import com.ratepay.challenge.repository.BugsRepository;
import com.ratepay.challenge.service.serviceInterface.BugsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Transactional
public class BugsServiceImpl implements BugsService {
    private final BugsRepository bugsRepository;

    private final ModelMapper modelMapper;

    public List<BugDAO> getBugs() {
        return bugsRepository.findAll();
    }

    public Bug getBugById(UUID issueId) {
        BugDAO bugDAO = bugsRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found issue with id = " + issueId));

        Bug bug = modelMapper.map(bugDAO, Bug.class);

        return bug;
    }

    public Bug createBug(Bug bug) {

        BugDAO bugDAO = modelMapper.map(bug, BugDAO.class);

        BugDAO response = bugsRepository.save(bugDAO);

        Bug bugResponse = modelMapper.map(response, Bug.class);

        return bugResponse;
    }

    public Bug updateBug(Bug bug) {

        BugDAO bugDAO = modelMapper.map(bug, BugDAO.class);

        BugDAO response = bugsRepository.save(bugDAO);

        Bug bugResponse = modelMapper.map(response, Bug.class);

        return bugResponse;
    }

    public void deleteBug(UUID issueId) {

        bugsRepository.deleteById(issueId);

    }
}
