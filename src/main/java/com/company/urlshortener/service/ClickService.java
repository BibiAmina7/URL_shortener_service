package com.company.urlshortener.service;

import com.company.urlshortener.model.Click;
import com.company.urlshortener.model.Url;
import com.company.urlshortener.repository.ClickRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClickService {

    private final ClickRepository clickRepository;

    public ClickService(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    @Transactional
    public void recordClick(Url url, String referrer, String ip) {
        Click click = new Click(url, referrer, ip);
        clickRepository.save(click);
    }

    public List<Click> findClicksForUrl(Url url) {
        return clickRepository.findByUrl(url);
    }
}
