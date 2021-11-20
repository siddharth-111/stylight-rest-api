package com.example.SpringBoot.service;

import com.example.SpringBoot.Model.Tutorial;

import java.util.List;

public interface TutorialService {
    public abstract List<Tutorial> getAllTutorials(String title);
    public abstract Tutorial getTutorialById(long id);
    public abstract Tutorial createTutorial(Tutorial tutorial) throws Exception;
    public abstract Tutorial updateTutorial(long id, Tutorial tutorial);
    public abstract void deleteTutorial(long id);
    public abstract void deleteAllTutorials();
    public abstract List<Tutorial> findByPublished();
}
