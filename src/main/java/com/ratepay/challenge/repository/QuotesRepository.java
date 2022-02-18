package com.ratepay.challenge.repository;

import com.ratepay.challenge.dao.QuoteDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface QuotesRepository extends JpaRepository<QuoteDAO, String> {

    @Modifying
    @Transactional
    public void deleteByCreationDateLessThan(Date closeTime);

    @Query(value = "SELECT * FROM Quotes q WHERE q.isin = ?1 AND q.creation_date BETWEEN ?2 and ?3 ORDER BY q.creation_date DESC", nativeQuery = true)
    List<QuoteDAO> findRelatedQuotesBetweenTimeNative(String isin, Date openTime, Date endTime);

    @Modifying
    @Transactional
    @Query(value = "DELETE from Quotes q where q.isin = ?1 and q.creation_date < ?2", nativeQuery = true)
    public void deleteByIsinCreationDateLessThanNative(String isin, Date creationDate);

    @Query(value = "SELECT * FROM Quotes q WHERE q.isin = ?1", nativeQuery = true)
    List<QuoteDAO> findQuotesByIsinNative(String isin);

}
