package com.pinguin.utils;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.model.DeveloperWeekPlan;
import com.pinguin.model.Plan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class StoryPlanningUtil {

    private int maximumStoryPoints = 10;

    static class SlidingWindowInfo {
        public int startIdx;
        public int endIdx;

        public SlidingWindowInfo(int startIdx, int endIdx)
        {
            this.startIdx = startIdx;
            this.endIdx = endIdx;
        }
    }

    public List<Plan> getPlanForDevelopers(List<Story> stories, List<Developer>  developers)
    {
        Collections.sort(stories, Comparator.comparing(Story::getPoints));

        List<Plan> planList = new ArrayList<>();

        int weekCount = 0;

        while(stories.size() != 0) {

            weekCount++;

            int maximumStoriesForDeveloper = stories.size() / (developers.size());
            List<DeveloperWeekPlan> developerWeekPlanList = new ArrayList<>();

            if (maximumStoriesForDeveloper == 0) {
                developers.forEach(developer -> {
                    if (stories.size() == 0)
                        return;

                    DeveloperWeekPlan developerWeekPlan = getDeveloperWeekPlan(developer, List.of(stories.get(0)));

                    developerWeekPlanList.add(developerWeekPlan);

                    removeStories(stories, 0, 0);
                });
            } else {
                developers.forEach(developer -> {
                    List<Story> storiesForDeveloper = getStoriesForDeveloper(stories, maximumStoriesForDeveloper);

                    DeveloperWeekPlan developerWeekPlan = getDeveloperWeekPlan(developer, storiesForDeveloper);

                    developerWeekPlanList.add(developerWeekPlan);
                });
            }

            Plan plan = new Plan();
            plan.setWeekNumber(weekCount);
            plan.setDeveloperWeekPlanList(developerWeekPlanList);

            planList.add(plan);
        }

        return planList;
    }

    private DeveloperWeekPlan getDeveloperWeekPlan(Developer developer, List<Story> storiesForDeveloper)
    {
        DeveloperWeekPlan developerWeekPlan = new DeveloperWeekPlan();
        developerWeekPlan.setDeveloperId(developer.getDeveloperId());
        developerWeekPlan.setName(developer.getName());
        developerWeekPlan.setStoryList(storiesForDeveloper);

        return developerWeekPlan;
    }

    List<Story> getStoriesForDeveloper(List<Story> stories, int maximumStoriesForDeveloper)
    {
        List<Story> storiesForDeveloper = new ArrayList<>();

        int allowedStoriesForDeveloper = getMaximumAllowedStoriesForDeveloper(stories, maximumStoriesForDeveloper);

        SlidingWindowInfo slidingWindowInfo = new SlidingWindowInfo(0, allowedStoriesForDeveloper - 1);

        updateSlidingWindowInfo(stories, allowedStoriesForDeveloper, slidingWindowInfo);

        for(int i = slidingWindowInfo.startIdx; i <= slidingWindowInfo.endIdx; i++)
        {
            storiesForDeveloper.add(stories.get(i));
        }

        removeStories(stories, slidingWindowInfo.startIdx, slidingWindowInfo.endIdx);

        return storiesForDeveloper;
    }

    void updateSlidingWindowInfo(List<Story> stories, int allowedStoriesForDeveloper, SlidingWindowInfo slidingWindowInfo)
    {
        int availableStoryPoints = maximumStoryPoints;

        for(int i = 0 ; i <= stories.size() - allowedStoriesForDeveloper ; i++)
        {
            int currentWindowStoryPoints = getSumOfStoryPoints(stories, i, i + allowedStoriesForDeveloper);

            if(currentWindowStoryPoints > maximumStoryPoints)
            {
                return;
            }

            int availableCurrentWindowStoryPoints = maximumStoryPoints - currentWindowStoryPoints;

            if(availableCurrentWindowStoryPoints < availableStoryPoints)
            {
                availableStoryPoints = availableCurrentWindowStoryPoints;
                slidingWindowInfo.startIdx = i;
                slidingWindowInfo.endIdx = i + (allowedStoriesForDeveloper - 1);
            }
        }
    }

    void removeStories(List<Story> stories, int startIndex, int endIndex)
    {
        if(startIndex != endIndex)
        {
            stories.subList(startIndex, endIndex + 1).clear();
        }
        else
        {
            stories.remove(startIndex);
        }
    }

    int getSumOfStoryPoints(List<Story> stories, int low, int high)
    {
        int sum = 0;
        while(low < high)
        {
            sum += stories.get(low).getPoints();
            low++;
        }

        return sum;
    }

    int getMaximumAllowedStoriesForDeveloper(List<Story> stories, int maximumStoriesForDeveloper)
    {
        int availableStoryPointsForDeveloper = maximumStoryPoints;
        int allowedStoriesForDeveloper = 0;

        for(int i = 0 ; i < maximumStoriesForDeveloper ; i++)
        {
            Story story = stories.get(i);

            if((availableStoryPointsForDeveloper - story.getPoints()) < 0)
            {
                return allowedStoriesForDeveloper;
            }

            availableStoryPointsForDeveloper -= story.getPoints();
            allowedStoriesForDeveloper++;
        }

        return allowedStoriesForDeveloper;
    }
}
