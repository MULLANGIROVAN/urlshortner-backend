package com.shortener.app.dto;

import java.time.LocalDateTime;

public class UrlResponse {
    private String originalUrl;
    private String shortUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private Long clickCount;

    public UrlResponse() {}

    public UrlResponse(String originalUrl, String shortUrl, String shortCode, LocalDateTime createdAt, Long clickCount) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.clickCount = clickCount;
    }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getShortCode() { return shortCode; }
    public void setShortCode(String shortCode) { this.shortCode = shortCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getClickCount() { return clickCount; }
    public void setClickCount(Long clickCount) { this.clickCount = clickCount; }
}
