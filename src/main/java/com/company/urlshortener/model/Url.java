package com.company.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls", indexes = {
        @Index(columnList = "short_code", name = "idx_short_code")
})
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="long_url", nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(name="short_code", unique = true)
    private String shortCode;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="click_count")
    private Long clickCount = 0L;

    // Constructors, getters, setters
    public Url() {}
    public Url(String longUrl) { this.longUrl = longUrl; }

    public Long getId() { return id; }
    public String getLongUrl() { return longUrl; }
    public void setLongUrl(String longUrl) { this.longUrl = longUrl; }
    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getClickCount() { return clickCount; }
    public void setClickCount(Long clickCount) { this.clickCount = clickCount; }
    public void incrementClickCount() { this.clickCount = this.clickCount + 1; }
}
