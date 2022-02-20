package com.pinguin.model;

import com.pinguin.entity.Story;

import java.util.List;
import java.util.UUID;

public class DeveloperWeekPlan {
    UUID developerId;

    List<Story> storyList;

    public UUID getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(UUID developerId) {
        this.developerId = developerId;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }
}
