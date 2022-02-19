package com.pinguin.service.serviceImpl;

import com.pinguin.entity.Bug;
import com.pinguin.entity.Story;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.repository.BugsRepository;
import com.pinguin.repository.StoryRepository;
import com.pinguin.service.serviceInterface.StoriesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Transactional
public class StoriesServiceImpl implements StoriesService {
    private final StoryRepository storyRepository;

    private final ModelMapper modelMapper;

    public List<Story> getStories() {

        List<Story> storyList = storyRepository.findAll();

        return storyList;
    }

    public Story getStoryById(UUID issueId) {
        Story story = storyRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + issueId));

        return story;
    }

    public Story createStory(Story story) {

        Story storyResponse = storyRepository.save(story);

        return storyResponse;
    }

    public Story updateStory(Story story) {

        storyRepository.findById(story.getIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + story.getIssueId()));

        Story storyResponse = storyRepository.save(story);

        return storyResponse;
    }

    public void deleteStory(UUID issueId) {

        storyRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found story with issue id = " + issueId));

        storyRepository.deleteById(issueId);

    }

    public void deleteAll() {
        storyRepository.deleteAll();
    }
}
