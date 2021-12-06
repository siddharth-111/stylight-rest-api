package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.CandlestickDAO;
import com.example.SpringBoot.dao.QuoteDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CandlesticksRepository extends JpaRepository<CandlestickDAO, String> {

    @Query(value = "SELECT * FROM Candlesticks c WHERE c.isin = ?1 AND c.open_timestamp >= ?2 and c.close_timestamp <= ?3 ORDER BY c.close_timestamp DESC", nativeQuery = true)
    List<CandlestickDAO> findRelatedCandlesticksBetweenTimeNative(String isin, Date openTime, Date endTime);
}
