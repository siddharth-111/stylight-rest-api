package com.pinguin.service.serviceImpl;


import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.model.Plan;
import com.pinguin.repository.DevelopersRepository;
import com.pinguin.repository.StoriesRepository;
import com.pinguin.utils.StoryPlanningUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@RequiredArgsConstructor
@Service
public class PlanningServiceImpl {

    private final StoriesRepository storiesRepository;

    private final DevelopersRepository developersRepository;

    private final StoryPlanningUtil storyPlanningUtil;

    public List<Plan> CreatePlan()
    {
        List<Story> storyList = storiesRepository.findByDeveloperIsNull();
        List<Developer> developerList = developersRepository.findAll();

        List<Plan> planList =  storyPlanningUtil.getPlanForDevelopers(storyList, developerList);

        return planList;

    }

}

