package com.company.urlshortener.controller;

import com.company.urlshortener.model.Url;
import com.company.urlshortener.service.ClickService;
import com.company.urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
public class RedirectController {

    private final UrlService urlService;
    private final ClickService clickService;

    public RedirectController(UrlService urlService, ClickService clickService) {
        this.urlService = urlService;
        this.clickService = clickService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode,
                                      @RequestHeader(value = "referer", required = false) String referrer,
                                      @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
                                      @RequestHeader(value = "User-Agent", required = false) String ua) {
        Url url = urlService.getAndIncrement(shortCode); // increments clickCount
        String ip = xff != null ? xff.split(",")[0].trim() : null;
        clickService.recordClick(url, referrer, ip);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getLongUrl()));
        return ResponseEntity.status(302).headers(headers).build();
    }
}

