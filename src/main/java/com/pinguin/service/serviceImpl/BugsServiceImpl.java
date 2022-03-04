package com.pinguin.service.serviceImpl;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.model.api.Assignment;
import com.pinguin.repository.DevelopersRepository;
import com.pinguin.service.serviceInterface.BugsService;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.entity.Bug;
import com.pinguin.repository.BugsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BugsServiceImpl implements BugsService {
    private final BugsRepository bugsRepository;

    private final DevelopersRepository developersRepository;


    public List<Bug> getBugs(String title) {

        List<Bug> bugList = StringUtils.isEmpty(title) ? bugsRepository.findAll() : bugsRepository.findByTitleContaining(title);

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

    public void assign(List<Assignment> assignments)
    {
        assignments.forEach(assignment -> {
            Bug bug = bugsRepository.findById(assignment.getIssueId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found bug with issue id = " + assignment.getIssueId()));

            Developer developer = developersRepository.findById(assignment.getDeveloperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found developer with id = " + assignment.getDeveloperId()));

            bug.setDeveloper(developer);

            bugsRepository.save(bug);
        });
    }

}
