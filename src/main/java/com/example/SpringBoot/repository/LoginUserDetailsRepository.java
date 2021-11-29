package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.LoginUserDetailsDAO;
import org.apache.juli.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserDetailsRepository extends JpaRepository<LoginUserDetailsDAO, Long> {
    Optional<LoginUserDetailsDAO> findByUsername(String username);
}
