package com.pinguin.service.serviceImpl;

import com.pinguin.entity.Story;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.repository.StoriesRepository;
import com.pinguin.service.serviceInterface.StoriesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StoriesServiceImpl implements StoriesService {
    private final StoriesRepository storiesRepository;

    public List<Story> getStories() {

        List<Story> storyList = storiesRepository.findAll();

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

    public void deleteStory(UUID issueId) {

        storiesRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + issueId));

        storiesRepository.deleteById(issueId);

    }

    public void deleteAll() {
        storiesRepository.deleteAll();
    }
}
