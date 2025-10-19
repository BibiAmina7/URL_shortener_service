package com.company.urlshortener.service;

import com.company.urlshortener.dto.UrlResponse;
import com.company.urlshortener.exception.UrlNotFoundException;
import com.company.urlshortener.model.Url;
import com.company.urlshortener.repository.UrlRepository;
import com.company.urlshortener.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.format.DateTimeFormatter;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public UrlResponse shortenUrl(String longUrl) {
        // If longUrl already exists, return existing
        var existing = urlRepository.findByLongUrl(longUrl);
        if (existing.isPresent()) {
            Url u = existing.get();
            String sUrl = String.format("%s/%s", baseUrl, u.getShortCode());
            return new UrlResponse(sUrl, u.getShortCode(), u.getLongUrl(), u.getCreatedAt(), u.getClickCount());
        }

        // Create entry to get ID
        Url url = new Url(longUrl);
        url = urlRepository.save(url);

        // Generate short code from ID (base62)
        String shortCode = Base62Encoder.encode(url.getId());
        url.setShortCode(shortCode);
        url = urlRepository.save(url);

        String shortUrl = String.format("%s/%s", baseUrl, shortCode);
        return new UrlResponse(shortUrl, shortCode, url.getLongUrl(), url.getCreatedAt(), url.getClickCount());
    }

    @Transactional
    public Url getAndIncrement(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortCode));
        url.incrementClickCount();
        urlRepository.save(url);
        return url;
    }

    public UrlResponse getInfo(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortCode));
        String sUrl = String.format("%s/%s", baseUrl, url.getShortCode());
        return new UrlResponse(sUrl, url.getShortCode(), url.getLongUrl(), url.getCreatedAt(), url.getClickCount());
    }
}

