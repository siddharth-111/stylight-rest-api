package com.pinguin.repository;

import com.pinguin.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DevelopersRepository extends JpaRepository<Developer, UUID> {
}
