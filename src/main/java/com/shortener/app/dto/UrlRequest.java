package com.shortener.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UrlRequest {

    @NotBlank(message = "URL must not be empty")
    @Pattern(regexp = "^(https?://).+", message = "URL must start with http:// or https://")
    private String originalUrl;

    private String customCode; // optional custom alias

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getCustomCode() { return customCode; }
    public void setCustomCode(String customCode) { this.customCode = customCode; }
}
