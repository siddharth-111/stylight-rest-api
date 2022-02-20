package com.pinguin.service.serviceImpl;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;
import com.pinguin.exception.ResourceNotFoundException;
import com.pinguin.repository.DevelopersRepository;
import com.pinguin.repository.StoriesRepository;
import com.pinguin.service.serviceInterface.DevelopersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
@Transactional
public class DevelopersServiceImpl implements DevelopersService {

    private final DevelopersRepository developersRepository;

    public List<Developer> getDevelopers() {

        List<Developer> developerList = developersRepository.findAll();

        return developerList;
    }

    public Developer getDeveloperById(UUID developerId) {
        Developer developer = developersRepository.findById(developerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found developer with id = " + developerId));

        return developer;
    }

    public Developer createDeveloper(Developer developer) {

        Developer developerResponse = developersRepository.save(developer);

        return developerResponse;
    }

    public Developer updateDeveloper(Developer developer) {

        developersRepository.findById(developer.getDeveloperId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found developer with id = " + developer.getDeveloperId()));

        Developer developerResponse = developersRepository.save(developer);

        return developerResponse;
    }

    public void deleteDeveloper(UUID developerId) {

        developersRepository.findById(developerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found developer with id =" + developerId));

        developersRepository.deleteById(developerId);

    }

    public void deleteAll() {
        developersRepository.deleteAll();
    }
}
