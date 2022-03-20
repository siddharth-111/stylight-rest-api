//package com.stylight.service.serviceImpl;
//
//
//import com.stylight.model.Plan;
//import com.stylight.repository.DevelopersRepository;
//import com.stylight.repository.StoriesRepository;
//import com.stylight.service.serviceInterface.PlanningService;
//import com.stylight.utils.StoryPlanningUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//
//import java.util.*;
//
//@RequiredArgsConstructor
//@Service
//public class PlanningServiceImpl implements PlanningService {
//
//    private final StoriesRepository storiesRepository;
//
//    private final DevelopersRepository developersRepository;
//
//    private final StoryPlanningUtil storyPlanningUtil;
//
//    public List<Plan> createPlan()
//    {
//        List<Story> storyList = storiesRepository.findByDeveloperIsNull();
//        List<Developer> developerList = developersRepository.findAll();
//
//        List<Plan> planList =  storyPlanningUtil.getPlanForDevelopers(storyList, developerList);
//
//        return planList;
//
//    }
//
//}
//
