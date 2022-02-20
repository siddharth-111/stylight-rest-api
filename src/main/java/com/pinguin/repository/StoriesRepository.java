package com.pinguin.repository;

import com.pinguin.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoriesRepository extends JpaRepository<Story, UUID> {
}
