package com.example.SpringBoot.repository;

import com.example.SpringBoot.dao.QuoteDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface QuotesRepository extends JpaRepository<QuoteDAO, String> {

    @Modifying
    @Transactional
    public void deleteByCreationDateLessThan(Date closeTime);


}
