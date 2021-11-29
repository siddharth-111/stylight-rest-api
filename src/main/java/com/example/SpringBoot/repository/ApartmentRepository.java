package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.ApartmentDAO;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<ApartmentDAO, Long> {

}
