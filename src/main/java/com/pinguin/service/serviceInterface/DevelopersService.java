package com.pinguin.service.serviceInterface;

import com.pinguin.entity.Developer;
import com.pinguin.entity.Story;

import java.util.List;
import java.util.UUID;

public interface DevelopersService {
    List<Developer> getDevelopers();
    Developer getDeveloperById(UUID developerId);
    Developer createDeveloper(Developer developer) ;
    Developer updateDeveloper(Developer developer);
    void deleteDeveloper(UUID developerId);
    void deleteAll();
}
