package com.example.SpringBoot.repository;


import com.example.SpringBoot.Model.Tutorial;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TutorialRepository {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Test
    public void testCreateReadDelete() {
        Tutorial tutorial = new Tutorial();
        tutorial.setTitle("Beethoven");
        tutorial.setDescription("1");
        tutorial.setPublished(true);

        tutorialRepository.save(tutorial);

        List<Tutorial> tutorials = tutorialRepository.findAll();
        assertEquals(tutorials.size(), 1);
        assertEquals(tutorials.get(0).getTitle(), "Beethoven");

        tutorialRepository.deleteAll();
        assertThat(tutorialRepository.findAll()).isEmpty();
    }
}
