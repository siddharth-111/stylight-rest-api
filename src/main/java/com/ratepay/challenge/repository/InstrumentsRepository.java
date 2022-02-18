package com.ratepay.challenge.repository;

import com.ratepay.challenge.dao.InstrumentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentsRepository extends JpaRepository<InstrumentDAO, String> {
}
