package com.shortener.app.service;

import com.shortener.app.dto.UrlRequest;
import com.shortener.app.dto.UrlResponse;
import com.shortener.app.exception.AliasAlreadyExistsException;
import com.shortener.app.exception.UrlNotFoundException;
import com.shortener.app.model.UrlMapping;
import com.shortener.app.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlShortenerService {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom random = new SecureRandom();

    private final UrlMappingRepository repository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.short-code-length:6}")
    private int codeLength;

    public UrlShortenerService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    public UrlResponse shortenUrl(UrlRequest request, com.shortener.app.model.User owner) {
        String code;

        if (request.getCustomCode() != null && !request.getCustomCode().isBlank()) {
            code = request.getCustomCode().trim();
            if (repository.existsByShortCode(code)) {
                throw new AliasAlreadyExistsException("Custom alias '" + code + "' is already taken");
            }
        } else {
            code = generateUniqueCode();
        }

        UrlMapping mapping = new UrlMapping(request.getOriginalUrl(), code);
        mapping.setOwner(owner);
        repository.save(mapping);

        return toResponse(mapping);
    }

    public String getOriginalUrl(String shortCode) {
        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("No URL found for code: " + shortCode));
        return mapping.getOriginalUrl();
    }

    @Transactional
    public String resolveAndIncrement(String shortCode) {
        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("No URL found for code: " + shortCode));
        mapping.setClickCount(mapping.getClickCount() + 1);
        repository.save(mapping);
        return mapping.getOriginalUrl();
    }

    public List<UrlResponse> getAllUrls() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<UrlResponse> getUrlsForUser(String username) {
        return repository.findByOwnerUsernameOrderByIdDesc(username).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode(codeLength);
        } while (repository.existsByShortCode(code));
        return code;
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private UrlResponse toResponse(UrlMapping mapping) {
        return new UrlResponse(
                mapping.getOriginalUrl(),
                baseUrl + mapping.getShortCode(),
                mapping.getShortCode(),
                mapping.getCreatedAt(),
                mapping.getClickCount()
        );
    }
}
