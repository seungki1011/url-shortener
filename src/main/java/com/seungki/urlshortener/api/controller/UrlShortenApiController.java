package com.seungki.urlshortener.api.controller;

import com.seungki.urlshortener.api.controller.dto.UrlShortenRequest;
import com.seungki.urlshortener.api.controller.dto.UrlShortenResponse;
import com.seungki.urlshortener.web.domain.UrlMapping;
import com.seungki.urlshortener.web.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlShortenApiController {

    private final UrlShortenService uss;

    @PostMapping("/shorten")
    public ResponseEntity<UrlShortenResponse> shortenUrl(@RequestBody @Validated UrlShortenRequest urlShortenRequest) {
        String originalUrl = urlShortenRequest.getUrl();
        UrlMapping urlMapping = uss.shortenUrl(originalUrl);
        UrlShortenResponse response = new UrlShortenResponse(urlMapping);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/detail/{shortcode}")
    public ResponseEntity<UrlShortenResponse> getDetail(@PathVariable String shortcode) {
        UrlMapping urlMapping = uss.findMatchingUrl(shortcode);
        UrlShortenResponse response = new UrlShortenResponse(urlMapping);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
