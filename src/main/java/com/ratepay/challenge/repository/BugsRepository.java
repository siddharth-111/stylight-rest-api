package com.ratepay.challenge.repository;

import com.ratepay.challenge.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BugsRepository extends JpaRepository<Bug, UUID> {
}
