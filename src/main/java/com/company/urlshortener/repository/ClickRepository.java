package com.company.urlshortener.repository;

import com.company.urlshortener.model.Click;
import com.company.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClickRepository extends JpaRepository<Click, Long> {
    List<Click> findByUrl(Url url);
    List<Click> findByUrlAndClickedAtBetween(Url url, LocalDateTime from, LocalDateTime to);
}
