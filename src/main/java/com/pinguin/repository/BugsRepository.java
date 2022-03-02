package com.pinguin.repository;

import com.pinguin.entity.Bug;
import com.pinguin.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BugsRepository extends JpaRepository<Bug, UUID> {
    List<Bug> findByTitleContaining(String title);
}
