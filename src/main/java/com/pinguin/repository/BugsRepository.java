package com.pinguin.repository;

import com.pinguin.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BugsRepository extends JpaRepository<Bug, UUID> {
}
