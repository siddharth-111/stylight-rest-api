package com.pinguin.model;

import com.pinguin.entity.Story;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DeveloperWeekPlan {
    UUID developerId;

    String name;

    List<Story> storyList;
}
