package com.example.SpringBoot.repository;

import com.example.SpringBoot.Model.Tutorial;
import com.example.SpringBoot.dao.ExchangeRatesDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRatesDAO, Long> {
    List<ExchangeRatesDAO> findAllByDate(Date publicationDate);

//    @Query("select td from ExchangeRates td join td.testDate ex where month(ex.date) = ?1")
//    List<ExchangeRatesDAO> getByMonth(int month);
}
