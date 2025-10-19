package com.company.urlshortener.dto;

import java.time.LocalDateTime;

public class UrlResponse {
    private String shortUrl;
    private String shortCode;
    private String longUrl;
    private LocalDateTime createdAt;
    private Long clickCount;

    public UrlResponse() {}
    public UrlResponse(String shortUrl, String shortCode, String longUrl, LocalDateTime createdAt, Long clickCount) {
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.clickCount = clickCount;
    }

    // getters & setters
    public String getShortUrl() { return shortUrl; }
    public String getShortCode() { return shortCode; }
    public String getLongUrl() { return longUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getClickCount() { return clickCount; }
}

