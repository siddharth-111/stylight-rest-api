package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.CandlestickDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandlesticksRepository extends JpaRepository<CandlestickDAO, String> {
}
