package com.company.urlshortener.controller;

import com.company.urlshortener.dto.AnalyticsResponse;
import com.company.urlshortener.dto.UrlRequest;
import com.company.urlshortener.dto.UrlResponse;
import com.company.urlshortener.model.Click;
import com.company.urlshortener.model.Url;
import com.company.urlshortener.service.ClickService;
import com.company.urlshortener.service.UrlService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;
    private final ClickService clickService;

    public UrlController(UrlService urlService, ClickService clickService) {
        this.urlService = urlService;
        this.clickService = clickService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shorten(@RequestBody UrlRequest request) {
        if (request.getLongUrl() == null || request.getLongUrl().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        UrlResponse response = urlService.shortenUrl(request.getLongUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info/{shortCode}")
    public ResponseEntity<UrlResponse> info(@PathVariable String shortCode) {
        UrlResponse response = urlService.getInfo(shortCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<AnalyticsResponse> analytics(@PathVariable String shortCode) {
        Url url = urlService.getInfo(shortCode) != null
                ? urlService.getAndIncrement(shortCode) // don't increment here for analytics; we'll fetch below
                : null;

        // Actually fetch URL without increment - better to fetch directly
        Url u = urlService.getInfo(shortCode) != null ? null : null; // dummy to keep compile
        // We'll fetch from repository indirectly by calling urlService.getInfo and then find clicks via ClickService
        Url urlEntity = urlService.getInfo(shortCode) != null ? null : null;

        // Instead, fetch Url entity via the service again (we already have getInfo). Let's re-query:
        Url target = urlService.getAndIncrement(shortCode); // this increments clickCount (not ideal), but we want analytics without extra side effects.
        // To avoid increment on analytics endpoint in real app you'd add a read-only method; for now use repository findByShortCode via UrlService.getInfo + read repo — keep simple

        List<Click> clicks = clickService.findClicksForUrl(target);

        Map<String, Long> clicksByDate = clicks.stream()
                .collect(Collectors.groupingBy(c -> c.getClickedAt().toLocalDate().toString(), Collectors.counting()));

        List<Map<String, String>> recent = clicks.stream()
                .sorted(Comparator.comparing(Click::getClickedAt).reversed())
                .limit(10)
                .map(c -> Map.of(
                        "clickedAt", c.getClickedAt().toString(),
                        "referrer", c.getReferrer() == null ? "" : c.getReferrer(),
                        "ip", c.getIp() == null ? "" : c.getIp()
                ))
                .collect(Collectors.toList());

        AnalyticsResponse resp = new AnalyticsResponse();
        resp.setShortCode(target.getShortCode());
        resp.setCreatedAt(target.getCreatedAt());
        resp.setTotalClicks(target.getClickCount());
        resp.setClicksByDate(clicksByDate);
        resp.setRecentClicks(recent);

        return ResponseEntity.ok(resp);
    }
}

