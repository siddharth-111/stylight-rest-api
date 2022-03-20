//package com.stylight.service;
//
//import com.stylight.model.Plan;
//import com.stylight.model.enums.StoryStatus;
//import com.stylight.repository.DevelopersRepository;
//import com.stylight.repository.StoriesRepository;
//import com.stylight.service.serviceInterface.PlanningService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class PlanningServiceTest {
//
//    @MockBean
//    private DevelopersRepository developersRepository;
//
//    @MockBean
//    private StoriesRepository storiesRepository;
//
//    @Autowired
//    PlanningService planningService;
//
//    List<Story> storyList;
//    List<Developer> developerList;
//
//    @BeforeEach
//    public void init() {
//
//        List<Integer> storyPoints = new ArrayList<>(Arrays.asList(1, 1, 2, 4, 5, 6, 2, 1, 2, 8));
//        storyList = new ArrayList<>();
//
//        storyPoints.forEach(point -> {
//            Story story = new Story();
//            story.setIssueId(UUID.randomUUID());
//            story.setTitle("Story title");
//            story.setDescription("Story Description");
//            story.setStoryStatus(StoryStatus.NEW);
//            story.setPoints(point);
//            storyList.add(story);
//        });
//
//        List<String> developerNames = new ArrayList<>(Arrays.asList("Sid", "Andrew", "John"));
//        developerList = new ArrayList<>();
//        developerNames.forEach(developer -> {
//            Developer newDeveloper = new Developer();
//            newDeveloper.setDeveloperId(UUID.randomUUID());
//            newDeveloper.setName(developer);
//            developerList.add(newDeveloper);
//        });
//
//    }
//
//    @AfterEach
//    public void teardown() {
//        storyList = new ArrayList<>();
//    }
//
//    @Test
//    public void shouldCreatePlan() {
//        when(storiesRepository.findByDeveloperIsNull()).thenReturn(storyList);
//        when(developersRepository.findAll()).thenReturn(developerList);
//
//        List<Plan> planList = planningService.createPlan();
//        assertEquals(planList.size(), 2);
//
//        // Test first week plan for all three developers
//        List<DeveloperWeekPlan> developerFirstWeekPlanList = planList.get(0).getDeveloperWeekPlanList();
//        assertEquals(developerFirstWeekPlanList.size(), 3);
//
//        DeveloperWeekPlan firstDeveloperFirstWeekPlan = developerFirstWeekPlanList.get(0);
//        assertEquals(firstDeveloperFirstWeekPlan.getStoryList().size(), 3);
//        assertEquals(firstDeveloperFirstWeekPlan.getStoryList().get(0).getPoints(), 2);
//        assertEquals(firstDeveloperFirstWeekPlan.getStoryList().get(1).getPoints(), 2);
//        assertEquals(firstDeveloperFirstWeekPlan.getStoryList().get(2).getPoints(), 4);
//
//        DeveloperWeekPlan secondDeveloperFirstWeekPlan = developerFirstWeekPlanList.get(1);
//        assertEquals(secondDeveloperFirstWeekPlan.getStoryList().size(), 3);
//        assertEquals(secondDeveloperFirstWeekPlan.getStoryList().get(0).getPoints(), 1);
//        assertEquals(secondDeveloperFirstWeekPlan.getStoryList().get(1).getPoints(), 2);
//        assertEquals(secondDeveloperFirstWeekPlan.getStoryList().get(2).getPoints(), 5);
//
//        DeveloperWeekPlan thirdDeveloperFirstWeekPlan = developerFirstWeekPlanList.get(2);
//        assertEquals(thirdDeveloperFirstWeekPlan.getStoryList().size(), 3);
//        assertEquals(thirdDeveloperFirstWeekPlan.getStoryList().get(0).getPoints(), 1);
//        assertEquals(thirdDeveloperFirstWeekPlan.getStoryList().get(1).getPoints(), 1);
//        assertEquals(thirdDeveloperFirstWeekPlan.getStoryList().get(2).getPoints(), 6);
//
//
//        // Test second week plan for all three developers
//        List<DeveloperWeekPlan> developerSecondWeekPlanList = planList.get(1).getDeveloperWeekPlanList();
//        assertEquals(developerSecondWeekPlanList.size(), 1);
//
//        DeveloperWeekPlan firstDeveloperSecondWeekPlan = developerSecondWeekPlanList.get(0);
//        assertEquals(firstDeveloperSecondWeekPlan.getStoryList().size(), 1);
//        assertEquals(firstDeveloperSecondWeekPlan.getStoryList().get(0).getPoints(), 8);
//
//    }
//}
