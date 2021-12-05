package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.InstrumentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentsRepository extends JpaRepository<InstrumentDAO, String> {
}
