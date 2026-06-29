package com.shortener.app.controller;

import com.shortener.app.dto.UrlRequest;
import com.shortener.app.dto.UrlResponse;
import com.shortener.app.model.User;
import com.shortener.app.repository.UserRepository;
import com.shortener.app.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlShortenerService service;
    private final UserRepository userRepository;

    public UrlController(UrlShortenerService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shorten(@Valid @RequestBody UrlRequest request, Authentication authentication) {
        User owner = userRepository.findByUsername(authentication.getName()).orElse(null);
        UrlResponse response = service.shortenUrl(request, owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UrlResponse>> getMine(Authentication authentication) {
        return ResponseEntity.ok(service.getUrlsForUser(authentication.getName()));
    }

    @GetMapping("/{shortCode}/info")
    public ResponseEntity<String> info(@PathVariable String shortCode) {
        return ResponseEntity.ok(service.getOriginalUrl(shortCode));
    }
}
