package com.example.SpringBoot.repository;

import com.example.SpringBoot.Model.Tutorial;
import com.example.SpringBoot.dao.ExchangeRatesDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRatesDAO, Long> {
    List<ExchangeRatesDAO> findAllByDate(Date publicationDate);
}
