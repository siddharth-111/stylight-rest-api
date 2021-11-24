package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.Tutorial;
import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.repository.TutorialRepository;
import com.example.SpringBoot.service.serviceInterface.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    @Override
    public List<Tutorial> getAllTutorials(String title) {

        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        if (title == null)
            tutorialRepository.findAll().forEach(tutorials::add);
        else
            tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

        return  tutorials;

    }

    @Override
    public Tutorial getTutorialById(long id) {
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
        return tutorial;
    }

    @Override
    public Tutorial createTutorial(Tutorial tutorial) throws Exception {
        Tutorial _tutorial = null;
        try{
            _tutorial = tutorialRepository
                    .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
        } catch (Exception e)
        {
            throw e;
        }
        return _tutorial;
    }

    @Override
    public Tutorial updateTutorial(long id, Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.isPublished());

        return tutorialRepository.save(_tutorial);
    }

    @Override
    public void deleteTutorial(long id) {
        tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete as there exists no tutorial with id = " + id));

        tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }

    @Override
    public List<Tutorial> findByPublished() {
        return tutorialRepository.findByPublished(true);
    }
}
