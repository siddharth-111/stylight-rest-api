package com.pinguin.repository;

import com.pinguin.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoriesRepository extends JpaRepository<Story, UUID> {
    List<Story> findByDeveloperIsNull();
    List<Story> findByTitleContaining(String title);
}
