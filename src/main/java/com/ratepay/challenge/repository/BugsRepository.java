package com.ratepay.challenge.repository;

import com.ratepay.challenge.dao.BugDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BugsRepository extends JpaRepository<BugDAO, UUID> {
}
