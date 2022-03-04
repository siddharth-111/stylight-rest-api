package com.pinguin.service.serviceImpl;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.model.api.Assignment;
import com.pinguin.repository.DevelopersRepository;
import com.pinguin.repository.StoriesRepository;
import com.pinguin.service.serviceInterface.StoriesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StoriesServiceImpl implements StoriesService {
    private final StoriesRepository storiesRepository;

    private final DevelopersRepository developersRepository;

    public List<Story> getStories(String title) {

        List<Story> storyList = StringUtils.isEmpty(title) ? storiesRepository.findAll() : storiesRepository.findByTitleContaining(title);

        return storyList;
    }

    public Story getStoryById(UUID issueId) {
        Story story = storiesRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + issueId));

        return story;
    }

    public Story createStory(Story story) {

        Story storyResponse = storiesRepository.save(story);

        return storyResponse;
    }

    public Story updateStory(Story story) {

        storiesRepository.findById(story.getIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + story.getIssueId()));

        Story storyResponse = storiesRepository.save(story);

        return storyResponse;
    }

    public void assign(List<Assignment> assignments)
    {
        assignments.forEach(assignment -> {
            Story story = storiesRepository.findById(assignment.getIssueId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + assignment.getIssueId()));

            Developer developer = developersRepository.findById(assignment.getDeveloperId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found developer with id = " + assignment.getDeveloperId()));

            story.setDeveloper(developer);

            storiesRepository.save(story);
        });
    }

    public void deleteStory(UUID issueId) {

        storiesRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + issueId));

        storiesRepository.deleteById(issueId);

    }

    public void deleteAll() {
        storiesRepository.deleteAll();
    }
}
