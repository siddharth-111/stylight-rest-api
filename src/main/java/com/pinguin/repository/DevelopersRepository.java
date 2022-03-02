package com.pinguin.repository;

import com.pinguin.entity.Bug;
import com.pinguin.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DevelopersRepository extends JpaRepository<Developer, UUID> {
    List<Developer> findByNameContaining(String title);
}
