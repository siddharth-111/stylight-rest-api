package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.QuoteDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotesRepository extends JpaRepository<QuoteDAO, String> {

}
