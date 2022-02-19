package com.pinguin.repository;

import com.pinguin.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BugsRepository extends JpaRepository<Bug, UUID> {
}
