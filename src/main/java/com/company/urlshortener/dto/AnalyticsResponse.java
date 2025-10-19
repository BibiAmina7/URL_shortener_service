package com.company.urlshortener.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AnalyticsResponse {
    private String shortCode;
    private Long totalClicks;
    private LocalDateTime createdAt;
    private Map<String, Long> clicksByDate; // date string -> count
    private List<Map<String, String>> recentClicks; // small list of recent click events

    public AnalyticsResponse() {}

    // getters & setters
    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }
    public Long getTotalClicks() { return totalClicks; }
    public void setTotalClicks(Long totalClicks) { this.totalClicks = totalClicks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Map<String, Long> getClicksByDate() { return clicksByDate; }
    public void setClicksByDate(Map<String, Long> clicksByDate) { this.clicksByDate = clicksByDate; }
    public List<Map<String, String>> getRecentClicks() { return recentClicks; }
    public void setRecentClicks(List<Map<String, String>> recentClicks) { this.recentClicks = recentClicks; }
}

