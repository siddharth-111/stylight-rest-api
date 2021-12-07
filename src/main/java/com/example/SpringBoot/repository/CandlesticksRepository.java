package com.example.SpringBoot.repository;

import java.util.Date;
import java.util.List;

import com.example.SpringBoot.dao.CandlestickDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface CandlesticksRepository extends JpaRepository<CandlestickDAO, String> {

    @Modifying
    @Transactional
    public void deleteByCreationDateLessThan(Date closeTime);

    @Query(value = "SELECT * FROM Candlesticks c WHERE c.isin = ?1 AND c.open_timestamp >= ?2 and c.close_timestamp <= ?3 ORDER BY c.open_timestamp ASC", nativeQuery = true)
    List<CandlestickDAO> findRelatedCandlesticksBetweenTimeNative(String isin, Date openTime, Date endTime);
}
