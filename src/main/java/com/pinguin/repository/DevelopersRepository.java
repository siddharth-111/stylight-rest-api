package com.pinguin.repository;

import com.pinguin.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DevelopersRepository extends JpaRepository<Developer, UUID> {
}
