package com.pinguin.service.serviceImpl;


import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.model.DeveloperWeekPlan;
import com.pinguin.model.Plan;
import com.pinguin.repository.DevelopersRepository;
import com.pinguin.repository.StoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Repository
@Transactional
public class PlanningServiceImpl {

    private final StoriesRepository storiesRepository;

    private final DevelopersRepository developersRepository;

    public List<Plan> CreatePlan()
    {
        List<Story> storyList = storiesRepository.findAll();
        List<Developer> developerList = developersRepository.findAll();

        List<Plan> planList =  CreatePlan(storyList, developerList);

        return planList;

    }

    List<Plan> CreatePlan(List<Story> stories, List<Developer>  developers)
    {
        Collections.sort(stories, Comparator.comparing(Story::getPoints));

        List<Plan> planList = new ArrayList<>();

        int count = 0;
        while(stories.size() != 0) {
            count++;

            int maxSize = stories.size() / (developers.size());
            List<DeveloperWeekPlan> developerWeekPlanList = new ArrayList<>();
            if (maxSize == 0) {
                developers.forEach(developer -> {
                    if (stories.size() == 0)
                        return;

                    DeveloperWeekPlan developerWeekPlan = new DeveloperWeekPlan();
                    developerWeekPlan.setDeveloperId(developer.getDeveloperId());
                    developerWeekPlan.setStoryList(List.of(stories.get(0)));

                    developerWeekPlanList.add(developerWeekPlan);

                    stories.remove(0);
                });
            } else {
                developers.forEach(developer -> {
                    List<Story> storiesForDeveloper = getWindow(stories, maxSize);

                    DeveloperWeekPlan developerWeekPlan = new DeveloperWeekPlan();
                    developerWeekPlan.setDeveloperId(developer.getDeveloperId());
                    developerWeekPlan.setStoryList(storiesForDeveloper);

                    developerWeekPlanList.add(developerWeekPlan);
                });
             }

            Plan plan = new Plan();
            plan.setWeekNumber(count);
            plan.setDeveloperWeekPlanList(developerWeekPlanList);

            planList.add(plan);
        }

        return planList;
    }

    static List<Story> getWindow(List<Story> stories, int maxWindow)
    {
        List<Story> result = new ArrayList<>();

        int maxWindowSize = getMaxWindowSize(stories, maxWindow);

        int maxDifference = 10;
        int startIndex = 0;
        int endIndex = (maxWindowSize - 1);

        for(int i = 0 ; i <= stories.size() - maxWindowSize ; i++)
        {
            int sum = getSum(stories, i, i + maxWindowSize);
            if(sum > 10)
            {
                break;
            }

            if((10 - sum) < maxDifference)
            {
                maxDifference = (10 - sum);
                startIndex = i;
                endIndex = i +  (maxWindowSize - 1);
            }
        }

        for(int i = startIndex ; i <= endIndex ; i++)
        {
            result.add(stories.get(i));
        }

        if(startIndex != endIndex)
        {
            stories.subList(startIndex, endIndex + 1).clear();
        }
        else
        {
            stories.remove(startIndex);
        }


        return result;
    }

    static int getSum(List<Story> stories, int low, int high)
    {
        int sum = 0;
        while(low < high)
        {
            sum += stories.get(low).getPoints();
            low++;
        }

        return sum;
    }

    static int getMaxWindowSize(List<Story> stories, int maxWindow)
    {
        int limit = 10;
        int count = 0;

        for(int i = 0 ; i < maxWindow ; i++)
        {
            Story story = stories.get(i);
            if((limit - story.getPoints()) < 0)
            {
                return count;
            }
            limit -= story.getPoints();
            count++;
        }

        return count;
    }
}

