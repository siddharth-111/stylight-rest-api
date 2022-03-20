package com.stylight.repository;

import com.stylight.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID> {
    Url findTopByOrderedParameter(String from);
    Url findTopByPrettyUrlContaining(String to);
}
